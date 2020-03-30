Vue.component('loading-icon', {
    props: ['loading'],
    template: '<i v-show="loading" class="fa fa-spin fa-circle-o-notch"></i>'
});

Vue.component('ibox-table-data', {
    template: '#iboxTableTemplate',
    props: {
        url: {
            type: String,
            required: true
        },
        title: {
            type: String,
            required: true
        },
        keyTitle: {
            type: String,
            default: 'Tipo'
        },
        valueTitle: {
            type: String,
            default: 'Cantidad'
        }
    },
    data: function(){
        return {
            countData: [],
            loading: true,
            order: {
                by: 'value',
                reverse: 1
            }
        }
    },
    methods: {
        loadData: function() {
            var _this = this;
            return $.ajax({
                url: _this.url,
                method: 'GET',
                success: function(response) {
                    _this.countData = response;
                    _this.loading = false;
                }
            });
        },
        refreshData: function() {
            this.loading = true;
            this.order.by = 'value';
            this.order.reverse = true;
            this.loadData();
        },
        changeDataOrder: function(by) {
            if(by != this.order.by) {
                this.order.by = by;
                this.order.reverse = -1;
            }
            else {
                this.order.reverse = this.order.reverse * -1;
            }
        }
    },
    ready: function() {
        this.loadData();
    }
});

new Vue({
    el: '#dashboard'
});
