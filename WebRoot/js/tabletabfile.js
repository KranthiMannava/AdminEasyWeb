var storeTableDataSets;
var tableModelDataSets;
var tableInformationGrid;
var storeComboDataSets;
Ext.require(['Ext.window.Window', 'Ext.tab.*', 'Ext.ux.ExportableGrid']);
Ext
    .onReady(function() {
	
	Ext
            .create(
                'Ext.form.Panel', {
                    id: 'tableGridForm',
                    renderTo: 'tableGridForm',
                    border: true,
                    width: 792,
                    height: 230,
					items: [
                        tableInformationGrid, {
                            xtype: 'button',
                            text: 'Export',
                            margin: '5 10 0 300',
                            listeners: {
                                click: function() {
                                    tableInformationGrid.export();
                                }
                            }
                        }]
						}
						);
	
        Ext
            .create(
                'Ext.form.Panel', {
                    id: 'tableForm',
                    renderTo: 'tableAnalysisFromPanel',
                    border: true,
                    width: 770,
                    height: 140,
               
					items : [ {
					xtype : 'fieldset',
					title : 'DataSet Selection',
					margin: '10 0 0 30',
                    items: [{
                            layout: 'hbox',
                            items: [{
                                xtype: 'combobox',
                                fieldLabel: 'Select Dataset',
                                store: storeComboDataSets,
                                id: 'selectedDataSet',
                                displayField: 'localDataset',
                                valueField: 'localDataset',
                                typeAhead: true,
                                queryMode: 'remote',
                                width: 350,
								style : 'margin-top:8px',
                                indent: true
                            }, {
                                xtype: 'button',
                                text: 'Get',
                                margin: '8 0 0 30',
                                listeners: {
                                    click: function() {
                                        debugger;
                                        var seletedTableDataSet = Ext
                                            .getCmp(
                                                'selectedDataSet')
                                            .getValue();
                                        storeTableDataSets
                                            .getProxy().url = 'AdminTableOfSelectedDatasetServlet';
                                        storeTableDataSets
                                            .getProxy().extraParams = {
                                                param1: seletedTableDataSet
                                            };
                                        storeTableDataSets
                                            .load();

                                    }
                                }
                            }]
                        }, {
                            xtype: 'displayfield',
                            fieldLabel: 'Current Alternative',
                            name: 'visitor_score',
							style : 'margin-top:8px',
                            value: 'Construction Information'
                        }
                    ],}]

                });

    });


storeComboDataSets = new Ext.data.JsonStore({
    extends: 'Ext.data.Model',
    fields: [{
        name: 'localDataset'
    }],
    proxy: new Ext.data.HttpProxy({
        url: 'AdminTableAnalysisDatasetServlet',
        reader: {
            type: 'json'
        }
    })

});

tableModelDataSets = Ext.create('Ext.data.Model', {
    fields: [{
        name: 'localInternalName',
        type: 'string',
        mapping: 'localInternalName'
    }, {
        name: 'localExternalName',
        type: 'string',
        mapping: 'localExternalName'
    }, {
        name: 'localCount',
        type: 'string',
        mapping: 'localCount'
    }, {
        name: 'localOccupiedSize',
        type: 'string',
        mapping: 'localOccupiedSize'
    }, {
        name: 'localBestSpaceUtilization',
        type: 'string',
        mapping: 'localBestSpaceUtilization'
    }, {
        name: 'localBlockOccupancyPercentile',
        type: 'string',
        mapping: 'localBlockOccupancyPercentile'
    }, {
        name: 'localSourceFile',
        type: 'string',
        mapping: 'localSourceFile'
    }],
});

storeTableDataSets = Ext.create('Ext.data.Store', {
    model: tableModelDataSets,
    fields: [{
        name: 'localInternalName',
    }, {
        name: 'localExternalName',
    }, {
        name: 'localCount',
    }, {
        name: 'localOccupiedSize',
    }, {
        name: 'localBestSpaceUtilization',
    }, {
        name: 'localBlockOccupancyPercentile',
    }, {
        name: 'lastChangedOn',
    }, {
        name: 'localSourceFile',
    }],
    autoLoad: false,
    proxy: {
        type: 'ajax',
        reader: {
            type: 'json'
        }
    }
});

tableInformationGrid = Ext.create('Ext.ux.ExportableGrid', {
    store: storeTableDataSets,
    xlsTitle: 'Table Analysis Details',

    columns: [{
        text: "Internal Name",
        width: 100,
        dataIndex: 'localInternalName',
        columnIndex: 'localInternalName',

    }, {
        text: "External Name",
        width: 110,
        dataIndex: 'localExternalName',
        columnIndex: 'localExternalName',

    }, {
        text: "Count",
        width: 60,
        dataIndex: 'localCount',
        columnIndex: 'localCount',

    }, {
        text: "Occupied Space(GB)",
        width: 140,
        dataIndex: 'localOccupiedSize',
        columnIndex: 'localOccupiedSize',

    }, {
        text: "Best Space Utilisation(GB)",
        width: 150,
        dataIndex: 'localBestSpaceUtilization',
        columnIndex: 'localBestSpaceUtilization',

    }, {
        text: "Block Occupency %",
        width: 120,
        dataIndex: 'localBlockOccupancyPercentile',
        columnIndex: 'localBlockOccupancyPercentile',

    }, {
        text: "Source File",
        width: 80,
        dataIndex: 'localSourceFile',
        columnIndex: 'localSourceFile',

    }],
    width: 760,
    height: 140,
    margin: '0 0 10 30',
});

Ext.create('Ext.data.Model', {
    fields: [{
        name: 'localDataset',
        type: 'string',
        mapping: 'localDataset'
    }],
});