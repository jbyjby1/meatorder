
new Vue({
    el: '#app',
    data: {
        inputThing: 'Hello Vue.js!',
        username: "",
        meats:[],
        tag: 0,
        queryStartDate: "",
        queryEndDate: "",
        queryUserView: [],
        queryMeatView: [],
        selectedUserOrders: {},
        selectedMeatOrders: {},
        queryCenter: "user",
        allOrdersSum: 0,
        shops: ["醉唐轩（盈创动力店）","食分钟（辉煌一店）","峨眉酒家（石景山店）"],
        selectedShop: "",
        dailyOrderLocked: false,
        dailyChickens: [],
        supportedOrderStatus: []
    },
    created: function () {
        toastr.options.positionClass = 'toast-top-right';
        this.meats = [];
        this.queryStartDate = new Date().Format("yyyy-MM-dd");
        this.queryEndDate = new Date().Format("yyyy-MM-dd");
        this.selectedShop = this.shops[0];
        this.updateDailyOrderLocked();
        this.queryDailyChickens();
        this.queryAllSupportedOrderStatus();
    },
    methods: {
        readMealsToday: function(){
            var self = this;
            if(self.username && self.username != ""){
                $.ajax({
                    url:"/orders",
                    data:{"date":"",
                        "username":self.username,
                        "shopName":self.selectedShop
                    },//请求的数据，以json格式
                    dataType:"json",//返回的数据类型
                    type:"get",//默认为get
                    success:function(data){
                        //成功方法，返回值用data接收
                        if(data.code == 0){
                            self.meats = data.data.orders;
                            if(data.message && data.message != ""){
                                toastr.success(data.message);
                            }
                        }else{
                            toastr.error(data.message);
                        }
                    },error:function(e){
                        //失败方法，错误信息用e接收
                        toastr.error("请求失败");
                    }
                });
            }
        },

        bingo: function(){
            var self = this;
            if(!self.username || self.username == "" || !self.meats || self.meats.length == 0 || JSON.stringify(self.meats) === '[]'){
                toastr.error("请完善订单信息");
                return;
            }
            for (let meat of self.meats){
                if(!meat.inputPrice){
                    toastr.error("请完善订单信息");
                    return;
                }
            }

            for(var i=0; i < self.meats.length; i++){
                self.meats[i].username = self.username;
                self.meats[i].shop = self.selectedShop;
            }
            $.ajax({
                url:"/orders",
                data:JSON.stringify({
                    "orders": self.meats
                }),//请求的数据，以json格式
                contentType: "application/json",
                dataType:"json",//返回的数据类型
                type:"post",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        if(data.message && data.message != ""){
                            setTimeout(function () {
                                toastr.success(data.message);
                            }, 500);
                        }else{
                            toastr.success("请求成功");
                        }
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                }
            });
        },
        //新点一样菜品
        addMeatItem: function(){
            var meatToAdd;
            if(this.meats){
                meatToAdd = this.meats;
            }else{
                meatToAdd = [];
            }
            var order = {
                "meat": "",
                "amount": 0,
                "price": 0
            }
            meatToAdd.push(order);
            this.meats = meatToAdd;
        },
        //刷新选择的菜品
        refreshSelect:function (event, item) {
            var self = this;
            if(event.keyCode==38||event.keyCode==40)return;
            //英文直接返回
            if((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 65 && event.keyCode <= 90)){
                return;
            }
            $.ajax({
                url:"/menus",
                data:{"meatName": item.meat, "shop": self.selectedShop},//请求的数据，以json格式
                dataType:"json",//返回的数据类型
                type:"get",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        self.$set(item, 'meatDataForSelect', data.data.menus)
                        if(data.message && data.message != ""){
                            toastr.success(data.message);
                        }
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                }
            });
        },
        getDisplayMenu: function(menu){
            if("堂食" != menu.meat){
                return menu.meat +  " - " + menu.flavor;
            }else{
                return menu.meat + " - " + menu.price + "元";
            }
        },
        setSelectedMeat: function(item, menuData){
            this.$set(item, 'meat', menuData.meat);
            this.$set(item, 'flavor', menuData.flavor);
            this.$set(item, 'inputPrice', menuData.price);
            this.clearSelection(item);
        },
        clearSelection: function(item){
            this.$set(item, 'meatDataForSelect', []);
        },
        //读取日期内所有的订单
        readAllOrders: function(){
            let self = this;
            let startIsoDate = self.getIsoTime(self.inputToDate(self.queryStartDate));
            let endIsoDate = self.getIsoTime(self.inputToDate(self.queryEndDate));
            //获取到所有active的状态
            let statusList = new Array();
            for (supportIndex in self.supportedOrderStatus){
                let support = self.supportedOrderStatus[supportIndex];
                if(support.active && support.active == true){
                    statusList.push(support);
                }
            }
            $.ajax({
                url:"/all-orders",
                data:{"startDate": startIsoDate,
                    "endDate":endIsoDate,
                    "shopName":self.selectedShop,
                    "statusListStr": JSON.stringify(statusList)
                },//请求的数据，以json格式
                dataType:"json",//返回的数据类型
                type:"get",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        self.queryMeatView = data.data.meatOrders;
                        self.queryUserView = data.data.personOrders;
                        //菜品总价格
                        let sum = 0;
                        for (let i in self.queryUserView){
                            sum = sum + self.queryUserView[i].inputPriceSum;
                        }
                        self.allOrdersSum = sum;
                        if(data.message && data.message != ""){
                            toastr.success(data.message);
                        }
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                }
            });
        },
        initQueryPage: function(){
            var self = this;
            $('.input-daterange').datepicker({
                autoclose: true,
                format: "yyyy-mm-dd",
                language:"zh-CN",
            }).on('changeDate', self.changeDate);
        },
        //切换以用户为中心的视图和以菜品为中心的视图
        changeView: function(){
            if(this.queryCenter == "user"){
                this.queryCenter = "meat";
            }else if(this.queryCenter == "meat"){
                this.queryCenter = "user";
            }
        },
        //查询并刷新本日菜单是否已被锁定
        updateDailyOrderLocked: function(){
            var self = this;
            $.ajax({
                url:"/events/dailyLockOrders",
                dataType:"json",//返回的数据类型
                type:"get",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        let events = data.data.events;
                        self.dailyOrderLocked = events != null && events.length > 0;
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                }
            });
        },
        //锁定本日菜单
        lockDailyOrder: function(){
            var self = this;
            $.ajax({
                url:"/events/dailyLockOrders",
                data:{},//请求的数据，以json格式
                dataType:"json",//返回的数据类型
                type:"post",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        toastr.success(data.message);
                    }else{
                        toastr.error(data.message);
                    }
                    self.updateDailyOrderLocked();
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                    this.updateDailyOrderLocked();
                }
            });
        },
        //解除本日菜单锁定
        unlockDailyOrder: function(){
            var self = this;
            $.ajax({
                url:"/events/dailyLockOrders",
                dataType:"json",//返回的数据类型
                type:"delete",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        toastr.success(data.message);
                    }else{
                        toastr.error(data.message);
                    }
                    self.updateDailyOrderLocked();
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                    this.updateDailyOrderLocked();
                }
            });
        },
        //返回是否允许解除锁定
        canUnlockDailyOrder: function(){
            if((new Date()).getHours() >= 18){
                return false;
            }else{
                return this.dailyOrderLocked;
            }
        },
        //查看今日吃鸡号码与大佬
        queryDailyChickens: function(){
            var self = this;
            $.ajax({
                url:"/chickens/daily",
                dataType:"json",//返回的数据类型
                type:"get",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        self.dailyChickens = data.data;
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求失败");
                    this.updateDailyOrderLocked();
                }
            });
        },
        //查询所有支持的状态
        queryAllSupportedOrderStatus: function(){
            var self = this;
            $.ajax({
                url:"/support/order-status",
                dataType:"json",//返回的数据类型
                type:"get",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        self.supportedOrderStatus = data.data.supportedOrderStatusList;
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("请求支持的订单失败失败");
                }
            });
        },
        //应用状态变化
        applyStatusSelected: function(dailyOrder){
            var self = this;
            $.ajax({
                url:"/orders/" + dailyOrder.id + "/status/" + dailyOrder.status,
                dataType:"json",//返回的数据类型
                type:"put",//默认为get
                success:function(data){
                    //成功方法，返回值用data接收
                    if(data.code == 0){
                        toastr.success(data.message);
                    }else{
                        toastr.error(data.message);
                    }
                },error:function(e){
                    //失败方法，错误信息用e接收
                    toastr.error("修改订单状态失败");
                }
            });
        },
        //查询时修改状态筛选条件
        changeSupportedFilter: function(supported){
            let self = this;
            for (orderStatusIndex in self.supportedOrderStatus) {
                let orderStatus = self.supportedOrderStatus[orderStatusIndex];
                if(orderStatus.orderStatus == supported.orderStatus){
                    //之前选中了就改成false，没选中就改成true
                    if(orderStatus.active && orderStatus.active == true){
                        orderStatus.active = false;
                    }else{
                        orderStatus.active = true;
                    }
                    break;
                }
            }
        },
        //点击查看用户详情
        showUserOrdersDetail: function(userOrders){
            console.log(userOrders);
            this.selectedUserOrders = userOrders;
        },
        //点击查看菜品详情
        showMeatOrdersDetail: function(meatOrders){
            console.log(meatOrders);
            this.selectedMeatOrders = meatOrders;
        },
        //设置起止时间
        changeDate: function(aaa) {
            //alert('change date');
            this.$set(this, 'queryStartDate', $('#startDate').val())
            this.$set(this, 'queryEndDate', $('#endDate').val())
        },
        //获取ISO规范时间
        getIsoTime: function(date){
            var d;
            if(date){
                d = date;
            }else{
                d = new Date();
            }
            //d.setHours(d.getHours(), d.getMinutes() - d.getTimezoneOffset());
            return d.toISOString();
        },
        inputToDate: function(inputDate){
            dateParams = inputDate.split('-');
            var dateStr = dateParams[1] + " " + dateParams[2] + "," + dateParams[0];
            return new Date(dateStr);
        },
        convertUTCTimeToLocalTime: function (UTCDateString) {
            if(!UTCDateString){
                return '-';
            }
            var date2 = new Date(UTCDateString);     //这步是关键
            return date2.Format("yyyy-MM-dd hh:mm:ss");
        },
        //切换右上角饭店
        setSelectedShop: function(shopName){
            this.selectedShop = shopName;
        },
        //切换左导航页签
        active: function(number){
            var self = this;
            self.tag = number;
            if(self.tag == 1){
                setTimeout(self.initQueryPage, 500);
            }
        },
    }
})

