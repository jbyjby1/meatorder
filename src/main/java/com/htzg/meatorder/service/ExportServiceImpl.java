package com.htzg.meatorder.service;

import com.htzg.meatorder.domain.MeatOrder;
import com.htzg.meatorder.domain.PersonOrder;
import com.htzg.meatorder.domain.RsAllOrders;
import com.htzg.meatorder.domain.modifier.ModifierExtended;
import com.htzg.meatorder.domain.modifier.OrderModifiers;
import com.htzg.meatorder.service.tools.DateService;
import com.htzg.meatorder.util.OrderUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExportServiceImpl implements ExportService {

    public static Logger logger = LoggerFactory.getLogger(ExportServiceImpl.class);

    @Value("${export.order.filename.prefix}")
    private String fileNamePrefix;

    @Override
    public boolean exportCurrentDayliOrders(RsAllOrders rsAllOrders, HttpServletResponse httpServletResponse) {
        //如果没有订单，直接返回
        if(CollectionUtils.isEmpty(rsAllOrders.getMeatOrders())){
            logger.error("Cannot export orders. MeatOrders null.");
            return false;
        }

        /******第一步，根据点菜的数量，获取到合适的模板***/
        List<MeatOrder> meatOrders = rsAllOrders.getMeatOrders();
        int orderSize = meatOrders.size();
        String fileName = null;
        if(orderSize <= 14){
            fileName = "order-template-short.xls";
        }else if(orderSize <= 37){
            fileName = "order-template.xls";
        }else{
            fileName = "order-template-long.xls";
        }
        try(InputStream excelFileInputStream = new ClassPathResource(fileName).getInputStream();
            //获取表格
            HSSFWorkbook workbook = new HSSFWorkbook(excelFileInputStream);
            OutputStream os = httpServletResponse.getOutputStream()){
            //总价
            float totalPrice = 0;
            //获取第一个sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            //创建单元格样式
            HSSFCellStyle cellStyle = getCellStyle(workbook);

            /******第二步，将订单数据写入到C3-F3至Cn~Fn中，名称--数量--单价--金额，计算总价写到总价区***/
            for (int i = 2; i < orderSize + 2; i++){
                //从第三行开始循环，每行从第三个数开始写入
                HSSFRow row = sheet.getRow(i);
                //本行数据来源：meat菜单的第i-3个数据
                MeatOrder currentRowData = meatOrders.get(i - 2);
                String name = currentRowData.getMeat();
                int amount = currentRowData.getAmount();
                float unitPrice = currentRowData.getUnitPrice();
                float inputPriceSum = currentRowData.getInputPriceSum();
                //从每行的C到每行的F，放入名称--数量--单价--金额
                HSSFCell nameCell = row.createCell(2);
                nameCell.setCellValue(name);
                nameCell.setCellStyle(cellStyle);
                HSSFCell amountCell = row.createCell(3);
                amountCell.setCellValue(amount);
                amountCell.setCellStyle(cellStyle);
                HSSFCell unitPriceCell = row.createCell(4);
                unitPriceCell.setCellValue(unitPrice);
                unitPriceCell.setCellStyle(cellStyle);
                HSSFCell inputPriceSumCell = row.createCell(5);
                inputPriceSumCell.setCellValue(inputPriceSum);
                inputPriceSumCell.setCellStyle(cellStyle);
                totalPrice += inputPriceSum;
            }

             /******第三步，将订单时间写入到B3，如果就一天，直接写当天+时间，否则写范围***/
            List<LocalDateTime> allOrderTimes = meatOrders.stream().flatMap(meatOrder -> meatOrder.getOrders().stream()).map(meatOrderExtended -> {
                //将所有的订单转换为时间
                LocalDateTime localDateTime = meatOrderExtended.getUpdateTime();
                return localDateTime;
            }).collect(Collectors.toList());
            //获取到最终要写入的时间
            String timeString = OrderUtils.getOrdersTimeStr(allOrderTimes);
            //写入到合并的单元格的左上位置，左上和右下位置分别为：B3 -- B最后一行
            HSSFRow timeRow = sheet.getRow(2);
            HSSFCell timeCell = timeRow.getCell(1);
            timeCell.setCellValue(timeString);
            timeCell.setCellStyle(cellStyle);

            /******第四步，将吃鸡者写入合并的单元格，左上和右下位置分别为：G3 -- G最后一行***/
            //查找吃鸡者
            String chickenGuy = rsAllOrders.getPersonOrders().stream().filter(personOrder -> {
                return personOrder.isLucky();
            }).map(PersonOrder::getUsername).reduce((a, b) -> a + "，" + b).orElseGet(() -> "");
            HSSFRow chickenRow = sheet.getRow(2);
            HSSFCell chickenCell = chickenRow.getCell(6);
            chickenCell.setCellValue(chickenGuy);
            chickenCell.setCellStyle(cellStyle);

            /******第五步，将总价写入K3***/
            HSSFRow totalPriceRow = sheet.getRow(2);
            HSSFCell totalPriceCell = totalPriceRow.createCell(10);
            totalPriceCell.setCellStyle(cellStyle);
            totalPriceCell.setCellValue(totalPrice);

            /******第六步，将各项立减写入I4-K7***/
            float allDiscountPrice = 0;

            //将查出来的所有折扣累加到结算单
            //保存所有天的折扣数量之和

            int currentRow = 3;
            Map<ModifierExtended, Long> allModifiersCount = rsAllOrders.getAllModifiersCount().getCountedModifiersMap();
            for (Map.Entry<ModifierExtended, Long> entry : allModifiersCount.entrySet()){
                HSSFRow totalModifierRow = sheet.getRow(currentRow);
                HSSFCell totalModifierNameCell = totalModifierRow.createCell(8);
                String displayName = entry.getKey().getDisplayName();
                totalModifierNameCell.setCellStyle(cellStyle);
                totalModifierNameCell.setCellValue(displayName);
                HSSFCell totalModifierCountCell = totalModifierRow.createCell(9);
                totalModifierCountCell.setCellStyle(cellStyle);
                totalModifierCountCell.setCellValue(entry.getValue());
                HSSFCell totalModifierValueCell = totalModifierRow.createCell(10);
                totalModifierValueCell.setCellStyle(cellStyle);
                totalModifierValueCell.setCellValue(entry.getValue() * entry.getKey().getModifierValue());

                currentRow++;
                allDiscountPrice += entry.getValue() * entry.getKey().getModifierValue();
            }

            /******第七步，将总价格写入K8***/
            HSSFRow totalBalanceAccountRow = sheet.getRow(7);
            HSSFCell totalBalanceAccountCell = totalBalanceAccountRow.createCell(10);
            totalBalanceAccountCell.setCellStyle(cellStyle);
            totalBalanceAccountCell.setCellValue(totalPrice + allDiscountPrice);

            /******第七步，设置文件名，返回导出数据***/
            String exportFileName = URLEncoder.encode(fileNamePrefix + timeString + ".xls", "UTF-8");
            httpServletResponse.setHeader("content-type", "application/octet-stream");
            httpServletResponse.setContentType("application/octet-stream");
            httpServletResponse.setHeader("Content-Disposition", "attachment; filename=" + exportFileName);
            workbook.write(os);
            os.flush();
            return true;
        } catch (IOException e){
            logger.error("Read or write file error.", e);
            return false;
        }
    }

    private HSSFCellStyle getCellStyle(HSSFWorkbook workbook){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        Font fontStyle = workbook.createFont();
        fontStyle.setBold(true); // 加粗
        fontStyle.setFontName("黑体"); // 字体
        fontStyle.setFontHeightInPoints((short) 11); // 大小
        // 将字体样式添加到单元格样式中
        cellStyle.setFont(fontStyle);
        // 边框，居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}
