var curentAlternative;
var selectedFileSystems;
var selectedFilePath;
var dataSetValue;
var superFileStoreDataModels;
var superfileStoreDataSets;
var superFileDesignGrid;

Ext.require(['Ext.ux.ExportableGrid']);


Ext
    .onReady(function() {
        var superGridForm = Ext.create('Ext.form.Panel', {
            id: 'gridFromId',
			renderTo: 'superFormGrid',
            border: true,
            width: 770,
            height: 180,
            style: 'padding: 0 10 10 10',
            items: [
                superFileDesignGrid, {
                        xtype: 'button',
                        text: 'Export',
                        disabled: true,
                        id: 'exportSuperFileDesigns',
                        margin: '20 0 0 300',
                        handler: function() {
                            superFileDesignGrid.export();
                        }
                    }]
            }
        );
        Ext.create(
                'Ext.form.Panel', {
                    id: 'newForm',
                    renderTo: 'superDataSetForm',
                    border: true,
                    width: 740,
                    height: 150,
					margin: '10 0 0 30',
					items : [ {
					xtype : 'fieldset',
					title : 'DataSet Selection',	
                    items: [{
                        xtype: 'combobox',
                        fieldLabel: 'Select Dataset',
                        store: datasetStore,
                        displayField: 'localDataset',
                        valueField: 'localDataset',
                        id: 'superDataSet',
                        typeAhead: true,
                        queryMode: 'remote',
                        selectOnFocus: true,
                        editable: false,
                        width: 350,
                        indent: true,
                        listeners: {
                            select: function(obj) {
                                dataSetValue = obj
                                    .getValue();
                                Ext.Ajax
                                    .request({
                                        type: 'json',
                                        url: 'AdminSuperFileGetFilePathServlet',
                                        method: 'GET',
                                        params: {
                                            param1: dataSetValue
                                        },
                                        success: function(
                                            response,
                                            operation) {
                                            var responseOfFileDataSet = Ext.util.JSON
                                                .decode(response.responseText);
                                            storeFilepaths
                                                .loadData(responseOfFileDataSet);

                                            for (key in responseOfFileDataSet) {
                                                if (responseOfFileDataSet
                                                    .hasOwnProperty(key)) {
                                                    var value = responseOfFileDataSet[key];
                                                    if (value
                                                        .hasOwnProperty('localSelectedfilePath')) {
                                                        selectedFilePath = value.localSelectedfilePath;
                                                    }
                                                    if (value
                                                        .hasOwnProperty('localCurrentAlternative')) {
                                                        curentAlternative = value.localCurrentAlternative;

                                                    }
                                                    if (value
                                                        .hasOwnProperty('localFileSystems')) {
                                                        selectedFileSystems = value.localFileSystems;

                                                    }

                                                }
                                            }
                                            Ext.ComponentQuery
                                                .query('#selectedPathId')[0]
                                                .setValue(selectedFilePath);

                                            Ext.ComponentQuery
                                                .query('#curentAlternativeId')[0]
                                                .setValue(curentAlternative);
                                            Ext
                                                .getCmp(
                                                    'superFileGetResults')
                                                .enable();
                                        }

                                    });
                            }
                        }
                    }, {
                        layout: 'hbox',
                        items: [{

                            xtype: 'combobox',
                            fieldLabel: 'Select File Path',
                            store: storeFilepaths,
                            displayField: 'localFileSystems',
                            valueField: 'localFileSystems',
                            queryMode: 'remote',
                            width: 350,
                            indent: true,

                        }, {
                            xtype: 'button',
                            text: 'Get',
                            disabled: true,
                            id: 'superFileGetResults',
                            margin: '0 0 0 30',
                            listeners: {
                                click: function() {
                                    debugger;
                                    var seletedSuperFileDataSet = Ext
                                        .getCmp(
                                            'superDataSet')
                                        .getValue();
                                    superfileStoreDataSets
                                        .getProxy().url = 'AdminSuperFileGetAllFilesDetailsServlet';
                                    superfileStoreDataSets
                                        .getProxy().extraParams = {
                                            param1: seletedSuperFileDataSet,
                                            param2: selectedFilePath
                                        };
                                    superfileStoreDataSets
                                        .load();
                                    Ext
                                        .getCmp(
                                            'exportSuperFileDesigns')
                                        .enable();
                                }

                            }
                        }]
                    }, {
                        xtype: 'displayfield',
                        fieldLabel: 'Selected FilePath:',
                        id: 'selectedPathId',
                        name: 'path',
                        value: ''
                    }, {
                        xtype: 'displayfield',
                        fieldLabel: 'Current Alternative',
                        id: 'curentAlternativeId',
                        name: 'visitor_score',
                        value: ''
                    }],
					}]

                });

    });

var storeFilepaths = Ext.create('Ext.data.JsonStore', {
    autoLoad: false,
    fields: [{
        name: 'localFileSystems'
    }, {
        name: 'localSelectedfilePath'
    }, {
        name: 'localCurrentAlternative'
    }]
});

var datasetStore = Ext.create('Ext.data.JsonStore', {
    extends: 'Ext.data.Model',
    fields: [{
        name: 'localDataset'
    }],
    proxy: new Ext.data.HttpProxy({
        url: 'AdminSuperFileAnalysisDatasetServlet',
        reader: {
            type: 'json'
        }
    })

});
var fileDataPathModel = Ext.create('Ext.data.Model', {
    fields: [{
        name: 'localFileSystems',
        type: 'string',
        mapping: 'localFileSystems'
    }, {
        name: 'localSelectedfilePath',
        type: 'string',
        mapping: 'localSelectedfilePath'
    }, {
        name: 'localCurrentAlternative',
        type: 'string',
        mapping: 'localCurrentAlternative'
    }],
});

superFileStoreDataModels = Ext.create('Ext.data.Model', {
    fields: [{
        name: 'fileName',
        type: 'string',
        mapping: 'fileName'
    }, {
        name: 'superFile',
        type: 'string',
        mapping: 'superFile'
    }, {
        name: 'totalComponents',
        type: 'string',
        mapping: 'totalComponents'
    }, {
        name: 'capacityGB',
        type: 'string',
        mapping: 'capacityGB'
    }, {
        name: 'usedGB',
        type: 'string',
        mapping: 'usedGB'
    }, {
        name: 'freeGB',
        type: 'string',
        mapping: 'freeGB'
    }, {
        name: 'status',
        type: 'string',
        mapping: 'status'
    }, {
        name: 'usedPercent',
        type: 'string',
        mapping: 'usedPercent'
    }]
});
superfileStoreDataSets = Ext.create('Ext.data.Store', {
    model: superFileStoreDataModels,
    fields: [{
        name: 'fileName',
    }, {
        name: 'superFile',
    }, {
        name: 'totalComponents',
    }, {
        name: 'capacityGB',
    }, {
        name: 'usedGB',
    }, {
        name: 'freeGB',
    }, {
        name: 'status',
    }, {
        name: 'usedPercent',
    }],
    autoLoad: false,
    proxy: {
        type: 'ajax',
        reader: {
            type: 'json'
        }
    }
});

superFileDesignGrid = Ext.create('Ext.ux.ExportableGrid', {
    store: superfileStoreDataSets,
    xlsTitle: 'Super Analysis Details',
    columns: [{
        text: "File",
        width: 100,
        dataIndex: 'fileName',
        columnIndex: 'fileName'

    }, {
        text: "Superfile",
        dataIndex: 'superFile',
        width: 100,
        columnIndex: 'superFile'

    }, {
        text: "Total Components",
        width: 100,
        dataIndex: 'totalComponents',
        columnIndex: 'totalComponents'

    }, {
        text: "Capacity",
        width: 90,
        dataIndex: 'capacityGB',
        columnIndex: 'capacityGB'

    }, {
        text: "Used",
        width: 90,
        dataIndex: 'usedGB',
        columnIndex: 'usedGB'

    }, {
        text: "Free",
        width: 70,
        dataIndex: 'freeGB',
        columnIndex: 'freeGB'

    }, {
        text: "Status",
        dataIndex: 'status',
        width: 90,
        columnIndex: 'status'

    }, {
        text: "Action Required",
        width: 100,
        dataIndex: 'usedPercent',
        columnIndex: 'usedPercent'

    }],
    width: 740,
    height: 85,
    margin: '30 0 20 30'

});
Ext.create('Ext.data.Model', {
    fields: [{
        name: 'localDataset',
        type: 'string',
        mapping: 'localDataset'
    }],
});