Ext.onReady(function() {
	Ext.create('Ext.grid.Panel', {
		renderTo : 'serverGrid',
		store: serverDataStore,
		columns : [ {
			text : "Server Name",
			dataIndex : 'serverName',
			dataIndex : 'serverName',
			width : 150,

		}, {
			text : "IP/ Host ",
			dataIndex : 'ip',
			dataIndex : 'ip',
			width : 150,

		}, {
			text : "Port",
			dataIndex : 'port',
			dataIndex : 'port',
			width : 150,

		}, {
			text : "Running?",
			dataIndex : 'running',
			dataIndex : 'running',
			width : 150,

		}, {
			text : "Status",
			dataIndex : 'status',
			dataIndex : 'status',
			width : 126,

		} ],
		width : 728,
		height : 250,
		margin: '15 20 0 30',
	});
	
	var serverDataModels = Ext.create('Ext.data.Model', {
    fields: [{
        name: 'serverName',
        type: 'string',
        mapping: 'serverName'
    }, {
        name: 'ip',
        type: 'string',
        mapping: 'ip'
    }, {
        name: 'running',
        type: 'string',
        mapping: 'running'
    }, {
        name: 'port',
        type: 'string',
        mapping: 'port'
    }, {
        name: 'status',
        type: 'string',
        mapping: 'status'
    }],
});

var serverDataStore = Ext.create('Ext.data.Store', {
    model: serverDataModels,
    fields: [{
        name: 'serverName',
    }, {
        name: 'ip',
    }, {
        name: 'port',
    }, {
        name: 'running',
    }, {
        name: 'status',
    }],
    autoLoad: false,
    proxy: {
        type: 'ajax',
        reader: {
            type: 'json'
        }
    }
});
	Ext.create('Ext.container.Container', {
		renderTo : 'serverButtons',
		width : 770,
		height : 30,
		margin : '0 0 0 250',
		layout : {
			type : 'hbox',
		},

		items : [ {
			xtype : 'button',
			text : 'Show Status',
			id : 'showStatusId',
			listeners : {
				click : function() {
					alert('You clicked the button!');
				}
			}
		},{
			xtype : 'button',
			text : 'Edit Config',
			id : 'editConfigId',
			listeners : {
				click : function() {
					alert('You clicked the button!');
				}
			}
		} ,
		{
			xtype : 'button',
			text : 'Refresh',
			id : 'refreshId',
			listeners : {
				click : function() {
					alert('You clicked the button!');
				}
			}
		}]
	});

});