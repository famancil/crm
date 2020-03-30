Vue.component('select-pais', {
    template: '#selectPais',
    props: {
        idPaisActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        selectName: {
            type: String,
            required: true
        }
    },
    data: function() {
        return {
            paises: []
        }
    },
    methods: {
        cargarPaises: function() {
            var _this = this;
            return $.ajax({
                method: 'get',
                url: '/geo/paises',
                success: function(data) {
                    _this.paises = data;
                }
            });
        }
    },
    ready: function() {
        this.cargarPaises();
    }
});

Vue.component('select-region', {
    template: '#selectRegion',
    props: {
        idRegionActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        idPaisActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        selectName: {
            type: String,
            required: true
        }
    },
    data: function() {
        return {
            regiones: []
        }
    },
    computed: {
        showSelect: function() {
            return this.idPaisActual == 56;
        }
    },
    methods: {
        cargarRegiones: function() {
            var _this = this;
            if(this.idPaisActual == 56) {
                return $.ajax({
                    method: 'get',
                    url: '/geo/regiones',
                    success: function(data) {
                        _this.regiones = data;
                    }
                });
            }
            else {
                this.idRegionActual = 0;
                this.regiones = [];
            }
        }
    },
    watch: {
        'idPaisActual': function() {
            this.cargarRegiones();
        }
    },
    ready: function() {
        this.cargarRegiones();
    }
});

Vue.component('select-provincia', {
    template: '#selectProvincia',
    props: {
        idProvinciaActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        idRegionActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        idPaisActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        selectName: {
            type: String,
            required: true
        }
    },
    data: function() {
        return {
            provincias: []
        }
    },
    computed: {
        showSelect: function() {
            return (this.idPaisActual == 56) && (this.idRegionActual != 0);
        }
    },
    methods: {
        cargarProvincias: function() {
            var _this = this;
            if(this.idPaisActual == 56 && this.idRegionActual != 0) {
                return $.ajax({
                    method: 'get',
                    url: '/geo/region/' + _this.idRegionActual + '/provincias',
                    success: function(data) {
                        _this.provincias = data;
                        _this.provincias.push({id: 0, nombre: 'Sin información'});
                    }
                });
            }
            else {
                this.idProvinciaActual = 0;
                this.provincias = [];
            }
        }
    },
    watch: {
        'idRegionActual': function() {
            this.cargarProvincias();
        }
    },
    ready: function() {
        this.cargarProvincias();
    }
});

Vue.component('select-comuna', {
    template: '#selectComuna',
    props: {
        idComunaActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        idProvinciaActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        idPaisActual: {
            type: Number,
            required: true,
            twoWay: true
        },
        selectName: {
            type: String,
            required: true
        }
    },
    data: function() {
        return {
            comunas: []
        }
    },
    computed: {
        showSelect: function() {
            return (this.idPaisActual == 56) && (this.idProvinciaActual != 0);
        }
    },
    methods: {
        cargarComunas: function() {
            var _this = this;
            if(this.idPaisActual == 56 && this.idProvinciaActual != 0) {
                return $.ajax({
                    method: 'get',
                    url: '/geo/provincia/' + _this.idProvinciaActual + '/comunas',
                    success: function(data) {
                        _this.comunas = data;
                        _this.comunas.push({codigo: 0, nombre: 'Sin información'});
                    }
                });
            }
            else {
                this.idComunaActual = 0;
                this.comunas = [];
            }
        }
    },
    watch: {
        'idProvinciaActual': function() {
            this.cargarComunas();
        }
    },
    ready: function() {
        this.cargarComunas();
    }
});

Vue.component('id-nacional', {
    template: '#identificadorNacional',
    props: {
        show: {
            type: Boolean,
            default: true
        }
    },
    data: function() {
        return {
            idValue: ''
        }
    },
    computed: {
        showComponent: function() {
            if(!this.show) {
                this.idValue = '';
            }
            return this.show;
        }
    }
});

Vue.component('id-extranjero', {
    template: '#identificadorExtranjero',
    props: {
        show: {
            type: Boolean,
            default: true
        }
    },
    data: function() {
        return {
            idValue: ''
        }
    },
    computed: {
        showComponent: function() {
            if(!this.show) {
                this.idValue = '';
            }
            return this.show;
        }
    }
});

new Vue({
    el: '#registrarEmpresa',
    data: {
        empresa: {
            paisActual: 56
        },
        sucursal: {
            paisActual: 56,
            regionActual: 0,
            provinciaActual: 0,
            comunaActual: 0
        }
    }
});