var fromDate;
var toDate;
var filterDate;
var formatFromDate;
var formatToDate;
var storedesignGrid;
var selectedData;
var designStatusFilter;
var rowMenu;
var rowStatusName;
var rowCount;
var seletedDesign;
var designDetailsWindow;
var designDetailsGrid;
var rowMenuofDesignDetailsGrid;

Ext.require([ 'Ext.window.Window', 'Ext.tab.*', 'Ext.ux.ExportableGrid' ]);

Ext
		.onReady(function() {
			

			var logOutImg = Ext.create('Ext.Img', {
				 style: {
                    width: '100px',
                    height: '10px'
					},
				src : 'images/logout.jpg'
			});

			Ext.create('Ext.container.Container', {
				width : 650,
				height : 30,
				layout : {
					type : 'hbox',
					padding : '5',

				},
				items : [ {
					xtype : 'button',
					title : 'File'
				}, {
					xtype : 'button',
					title : 'Reports'
				} ]
			});

	

			var tabpanel = Ext.create('Ext.form.Panel', {
				width : 770,
				height : 120,			
				
				renderTo : 'formtab1',
				autoScroll : true,
				items : [ {
					xtype : 'fieldset',
					title : 'Design Creation Date',	
					margin: '10 15 0 30',
					
					layout : {
						type : 'hbox',
					},
					items : [ {
						layout : {
							type : 'vbox',
						},
						items : [ {
							layout : {
								type : 'hbox',
							},
							items : [ {
								xtype : 'datefield',
								fieldLabel : 'From Date',
								name : 'from_date',
								margin: '0 30 0 10',
								id : 'fDate',
								maxValue : new Date(),
								listeners : {
									select : function(combo, value) {
										fromdate = value;
									}
								}
							}, {
								xtype : 'datefield',
								fieldLabel : 'To Date',
								name : 'to_date',
								id : 'tDate',
								value : new Date(),
								listeners : {
									select : function(combo, value) {
										todate = value;
									}
								}
							} ]
						}, {
							layout : {
								type : 'hbox',
							},
							items : [ {
								xtype : 'displayfield',
								fieldLabel : 'Current Filter',
								name : 'currentFiler',
								itemId : 'filterDateId',
								value : '',
								width : 500,
								margin: '20 0 0 10'

							} ]
						} ]
					},{
						layout : {
							type : 'vbox',
						},
						items : [ {
							xtype : 'button',
							text : 'Add Filter',
							id : 'submit',
							margin : '0 10 0 30',
							handler : function(button) {
								Ext.getCmp('removeFilter').enable();
								fromDate = Ext.getCmp('fDate').getValue();
								toDate = Ext.getCmp('tDate').getValue();
								if (fromDate != null
										&& toDate != null) {

									formatFromDate = formatFDate(fromDate);
									function formatFDate(fromDate) {
										var fDate = fromDate.getDate();
										var fMonth = fromDate.getMonth() + 1;
										var fYear = fromDate.getFullYear();
										if (fDate < 10) {
											fDate = '0' + fDate;
										}
										if (fMonth < 10) {
											fMonth = '0' + fMonth;
										}
										return fDate + '/' + fMonth + '/' + fYear;
										}

									formatToDate = formatTDate(toDate);
									function formatTDate(toDate) {
										var tDate = toDate.getDate();
										var tMonth = toDate.getMonth() + 1;
										var tYear = toDate.getFullYear();
										if (tDate < 10) {
											tDate = '0' + tDate;
										}
										if (tMonth < 10) {
											tMonth = '0' + tMonth;
										}
										return tDate + '/' + tMonth + '/' + tYear
									}

							filterDate = formatFromDate.concat(" " + "  to  " + " " + formatToDate);
									Ext.ComponentQuery
											.query('#filterDateId')[0]
											.setValue(filterDate);

								} else {
									Ext.ComponentQuery
											.query('#filterDateId')[0]
											.setValue('None');
								}

								Ext
										.getCmp(
												'getDesignsId')
										.enable();

							}

						} ,{
							items : [ {
								xtype : 'button',
								text : 'Remove Filter',
								margin : '20 0 0 30',
								itemId : 'removeDateId',
								id : 'removeFilter',
								disabled : true,
								handler : function(
										button) {
									Ext.ComponentQuery
											.query('#filterDateId')[0]
											.setValue("None");
									Ext
											.getCmp(
													'removeFilter')
											.enable();
								}
							} ]
						} ]
					} 
					
					
					]
				}
				,  ]
			});

			Ext.create('Ext.data.Model', {
				fields : [ {
					name : 'designStatus',
					type : 'string',
					mapping : 'designStatus'
				}, {
					name : 'noOfDesigns',
					type : 'string',
					mapping : 'noOfDesigns'
				} ],
			});

			// design grid store
			storedesignGrid = Ext.create('Ext.data.Store', {
				extend : 'Ext.data.Model',
				fields : [ {
					name : 'designStatus'

				}, {
					name : 'noOfDesigns'
				} ],
				autoLoad : false,
				proxy : {
					type : 'ajax',
					reader : {
						type : 'json'
					}
				}
			});

			// design Details Model

			designDetailsModel = Ext.create('Ext.data.Model', {
				fields : [ {
					name : 'designId',
					type : 'string',
					mapping : 'designId'
				}, {
					name : 'designName',
					type : 'string',
					mapping : 'designName'
				}, {
					name : 'projectName',
					type : 'string',
					mapping : 'projectName'
				}, {
					name : 'designStatus',
					type : 'string',
					mapping : 'designStatus'
				}, {
					name : 'owner',
					type : 'string',
					mapping : 'owner'
				}, {
					name : 'createdOn',
					type : 'string',
					mapping : 'createdOn'
				}, {
					name : 'lastChangedOn',
					type : 'string',
					mapping : 'lastChangedOn'
				}, {
					name : 'projectType',
					type : 'string',
					mapping : 'projectType'
				}, {
					name : 'age',
					type : 'string',
					mapping : 'age'
				} ],
			});

			// designDetails Store

			storeDesignDetails = Ext.create('Ext.data.Store', {
				model : designDetailsModel,
				fields : [ {
					name : 'designId',

				}, {
					name : 'designName',
				}, {
					name : 'projectName',
				}, {
					name : 'designStatus',
				}, {
					name : 'owner',
				}, {
					name : 'createdOn',
				}, {
					name : 'lastChangedOn',
				}, {
					name : 'projectType',
				}, {
					name : 'age',
				} ],
				autoLoad : false,
				proxy : {
					type : 'ajax',
					reader : {
						type : 'json'
					}
				}
			});

			// Model Alternative Data

			var designAlternativeDetailsModel = Ext.create('Ext.data.Model', {
				fields : [ {
					name : 'datasetName',
					type : 'string',
					mapping : 'datasetName'
				}, {
					name : 'alternativePath',
					type : 'string',
					mapping : 'alternativePath'
				}, {
					name : 'exists',
					type : 'string',
					mapping : 'exists'
				} ]
			});

			// Store Alternative Data

			var storeAlternativeDesignDetails = Ext.create('Ext.data.Store', {
				model : designAlternativeDetailsModel,
				fields : [ {
					name : 'datasetName',

				}, {
					name : 'alternatePath',
				}, {
					name : 'exists',
				} ],
				autoLoad : false,
				proxy : {
					type : 'ajax',
					reader : {
						type : 'json'
					}
				}

			});
			// Alternative Grid

			var designAlternativeDetailsGrid = Ext.create(
					'Ext.ux.ExportableGrid', {
						store : storeAlternativeDesignDetails,
						xlsTitle : 'Show Alternative Design Details',

						forceFit : true,
						width : '100%',
						layout : {
							type : 'fit'
						},
						columns : [ {
							text : "DataSet",
							dataIndex : 'datasetName',
							width : 100,
							columnIndex : 'datasetName'

						}, {
							text : "Alternative Path",
							dataIndex : 'alternatePath',
							width : 300,
							columnIndex : 'alternatePath'

						}, {
							text : "Exists",
							dataIndex : 'exists',
							width : 70,
							columnIndex : 'exists'

						} ],
					});

			var excelGenerateImgForAlternativeDesignDetails = Ext.create(
					'Ext.Img', {
						src : 'images/xcel.png',
						height : 25,
						width : 25,
						draggable : true,
						listeners : {
							el : {
								click : function() {
									designAlternativeDetailsGrid.export();
								}
							},
							afterrender : function(c) {
								Ext.create('Ext.tip.ToolTip', {
									target : c.getEl(),
									html : 'Click for Excel Report'
								});
							}
						}
					});

			// Row itemcontext Details

			var designGridValue;
			rowMenuofDesignDetailsGrid = Ext
					.create(
							'Ext.menu.Menu',
							{
								height : 158,
								width : 180,
								items : [
										{
											text : 'Clear Selection',
											listeners : {
												click : function(btn) {
													designDetailsGrid
															.getSelectionModel()
															.deselectAll();
												}
											}
										},
										{
											text : 'Select All',
											listeners : {
												click : function(btn, state) {
													if (state == true) {
														designDetailsGrid
																.getSelectionModel()
																.selectAll();
													}
												}
											}
										},
										{
											text : 'Delete Selected design',
											listeners : {
												click : function() {
													storeDesignDetails
															.getProxy().url = 'AdminDesignDeleteSelectedDesignServlet';
													storeDesignDetails
															.getProxy().extraParams = {
														param1 : designGridValue,
														param2 : "no",
													};
													Ext.Msg
															.confirm(
																	'Confirm to delete',
																	'Want to delete record?',
																	function(
																			button) {

																		if (button == 'yes') {
																			storeDesignDetails
																					.remove(designGridValue);
																			designDetailsGrid
																					.getView()
																					.refresh();
																			storeDesignDetails
																					.load();

																		}
																	});
												}
											}
										},
										{
											text : 'Delete All Designs'
										},
										{
											text : 'Show Alternative Details',
											listeners : {
												click : function() {
													storeAlternativeDesignDetails
															.getProxy().url = 'AdminDesignAlternativeDetailsServlet';
													storeAlternativeDesignDetails
															.getProxy().extraParams = {
														param1 : designGridValue,

													};
													storeAlternativeDesignDetails
															.load();
													designAlternativeDetailsGrid
															.getView()
															.on(
																	'refresh',
																	function() {
																		var cellheight = 25;
																		var titleHeight = 50;
																		designAlternativeDetailsGrid
																				.setHeight(storeAlternativeDesignDetails
																						.getCount()
																						* cellheight
																						+ titleHeight);
																	});

													var designAlternativeDetailsWindow = Ext
															.create(
																	'widget.window',
																	{
																		width : 800,
																		x : 200,
																		y : 200,
																		closeAction : 'hide',
																		title : "Show Alternative Design Details",

																		items : [
																				excelGenerateImgForAlternativeDesignDetails,
																				designAlternativeDetailsGrid,

																		/*
																		 * {
																		 * xtype :
																		 * 'button',
																		 * text :
																		 * 'Export',
																		 * margin :
																		 * '20 0
																		 * 0
																		 * 300',
																		 * listeners : {
																		 * click :
																		 * function() {
																		 * designAlternativeDetailsGrid.export(); } } }
																		 */]
																	});
													designAlternativeDetailsWindow
															.show();
												}
											}
										} ]
							});

			// DesignDetails Grid

			designDetailsGrid = Ext.create('Ext.ux.ExportableGrid', {
				store : storeDesignDetails,
				xlsTitle : 'Design Details',
				columns : [ {
					text : "Design Id",
					dataIndex : 'designId',
					width : 70,
					columnIndex : 'designId'

				}, {
					text : "Design Name",
					dataIndex : 'designName',
					format : '0',
					width : 100,
					columnIndex : 'designName'
				}, {
					text : "Project Name",
					dataIndex : 'projectName',
					width : 100,
					columnIndex : 'projectName'

				}, {
					text : "Design Status",
					dataIndex : 'designStatus',
					format : '0',
					width : 100,
					columnIndex : 'designStatus'
				}, {
					text : "Owner",
					dataIndex : 'owner',
					width : 70,
					columnIndex : 'owner'

				}, {
					text : "Created On",
					dataIndex : 'createdOn',
					format : '0',
					width : 70,
					columnIndex : 'createdOn'
				}, {
					text : "Last ChangedOn",
					dataIndex : 'lastChangedOn',
					width : 80,
					columnIndex : 'lastChangedOn'

				}, {
					text : "Project Type",
					dataIndex : 'projectType',
					format : '0',
					width : 130,
					columnIndex : 'projectType'
				}, {
					text : "Age",
					dataIndex : 'age',
					format : '0',
					width : 70,
					columnIndex : 'age'
				} ],
				forceFit : true,
				multiSelect : true,
				viewConfig : {
					stripeRows : true,
					listeners : {
						itemcontextmenu : function(view, rec, node, index, e) {
							e.stopEvent();
							rowMenuofDesignDetailsGrid.showAt(e.getXY());
							return false;
						},
						select : function(selModel, record, index, options) {
							designGridValue = record.get('designId');
						}
					}
				},
				height : 100
			});
			var deleteProjectCheckbox = Ext.create('Ext.form.field.Checkbox', {
				boxLabel : 'Delete Project?'
			});
			var excelGenerateImg = Ext.create('Ext.Img', {
				src : 'images/xcel.png',
				height : 25,
				width : 25,
				draggable : true,
				listeners : {
					el : {
						click : function() {
							designDetailsGrid.export();
						}
					},
					afterrender : function(c) {
						Ext.create('Ext.tip.ToolTip', {
							target : c.getEl(),
							html : 'Click for Excel Report'
						});
					}
				}
			});

			rowMenu = Ext
					.create(
							'Ext.menu.Menu',
							{
								height : 54,
								width : 140,
								items : [
										{
											text : 'Clear Selection',
											listeners : {
												click : function(btn) {
													designGrid
															.getSelectionModel()
															.deselectAll();
												}
											}

										},
										{
											text : 'Show Design Details',
											listeners : {
												click : function() {
													storeDesignDetails
															.getProxy().url = 'AdminDesignDetailsServlet';
													storeDesignDetails
															.getProxy().extraParams = {
														param1 : seletedDesign,
														param2 : formatFromDate,
														param3 : formatToDate,
														param4 : designStatusFilter,
														param5 : rowStatusName

													};
													debugger;
													storeDesignDetails.load();

													designDetailsGrid
															.getView()
															.on(
																	'refresh',
																	function() {
																		var cellheight = 25;
																		var titleHeight = 50;
																		designDetailsGrid
																				.setHeight(storeDesignDetails
																						.getCount()
																						* cellheight
																						+ titleHeight);
																	});

													designDetailsWindow = Ext
															.create(
																	'widget.window',
																	{
																		width : 800,
																		closable : true,
																		closeAction : 'hide',
																		title : "Show Design Details",

																		items : [
																				excelGenerateImg,
																				designDetailsGrid /*
																									 * ,{
																									 * xtype :
																									 * 'button',
																									 * text :
																									 * 'Export',
																									 * id :
																									 * 'exportDesignDetails',
																									 * margin :
																									 * '20
																									 * 0 0
																									 * 600',
																									 * listeners : {
																									 * handler :
																									 * function() {
																									 * designDetailsGrid.export(); }} }
																									 */]
																	});
													designDetailsWindow.show();
												}
											}
										} ]
							});

			// Design Grid

			var designGrid = Ext.create('Ext.ux.ExportableGrid', {
				store : storedesignGrid,
				xlsTitle : 'Design Details',
				margin: '10 0 0 30',
				columns : [ {
					text : "Design Status",
					dataIndex : 'designStatus',
					width : 360,
					columnIndex : 'designStatus'

				}, {
					text : "Number of Designs",
					dataIndex : 'noOfDesigns',
					format : '0',
					width : 360,
					columnIndex : 'noOfDesigns'
				} ],
				multiSelect : true,
				renderTo : 'gridtab1',
				width : 720,
				height : 160,
				viewConfig : {
					stripeRows : true,
					listeners : {
						itemcontextmenu : function(view, rec, node, index, e) {
							e.stopEvent();
							rowMenu.showAt(e.getXY());
							return false;
						},
						select : function(selModel, record, index, options) {
							rowStatusName = record.get('designStatus');
							// rowCount = record.get('noOfDesigns');
						}
					}
				},

			});
			Ext
					.create(
							'Ext.container.Container',
							{
								renderTo : 'griddatacall',
								width : 500,
								height : 30,
								margin : '0 0 0 150',
								layout : {
									type : 'hbox',
									pack : 'center',
									align : 'middle'

								},
								defaults : {
									anchor : '100%'
								},
								items : [
										{
											xtype : 'button',
											text : 'GET',
											disabled : true,
											id : 'getDesignsId',
											listeners : {
												click : function() {
													seletedDesign = Ext.getCmp(
															"selectId")
															.getValue();
													var filterDates = Ext.ComponentQuery
															.query('#filterDateId')[0]
															.getValue();
													if (filterDates != 'None') {
														designStatusFilter = 'yes';
													} else {
														designStatusFilter = 'no';
													}
													storedesignGrid.getProxy().url = 'AdminDesignDataServlet';
													storedesignGrid.getProxy().extraParams = {
														param1 : formatFromDate,
														param2 : formatToDate,
														param3 : seletedDesign,
														param4 : designStatusFilter
													};
													storedesignGrid.load();
													Ext.getCmp('exportDesigns')
															.enable();
												}
											}
										}, {
											xtype : 'button',
											text : 'Export',
											id : 'exportDesigns',
											disabled : true,
											handler : function() {
												designGrid.export();
											}
										} ]
							});

			Ext.onReady(function() {
				Ext.designs = [ [ 'Designs by Status' ],
						[ 'Designs without Alternatives' ],
						[ 'Designs without Projects' ]

				];
				var storeOption = Ext.create('Ext.data.ArrayStore', {
					fields : [ 'data' ],
					data : Ext.designs
				});

				selectedData = Ext.create('Ext.form.ComboBox', {
					xtype : 'combo',
					renderTo : 'selecttab1',
					store : storeOption,
					fieldLabel : 'Select Option',
					id : 'selectId',
					displayField : 'data',
					typeAhead : true,
					style : 'marginLeft:20px, marginTop:8px',
					width: 350,
					margin: '15 0 0 30',
					indent : true,
					listeners : {
						select : function(combo, records) {

							var finalvalue = selectedData.getValue();

						}

					}

				});

			});

		});
