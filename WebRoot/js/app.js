Ext
    .onReady(function() {
        var tabHeader = Ext.widget('tabpanel', {
            
            style: 'border: solid black 1px',
            width: 850,
            height: 430,
            activeTab: 0,
            defaults: {
                bodyPadding: 10
            },
            items: [{
                contentEl: 'designTab',
                title: 'Design Analysis'
            }, {
                contentEl: 'superAnalysisTab',
                title: 'Super Analysis'
            }, {
                contentEl: 'tableAnalyisTab',
                title: 'Table Analysis'
            }, {
                contentEl: 'serverAnalysisTab',
                title: 'Server Status'
            }]
        });
		var changingImage = Ext.create('Ext.Img', {
				width : 788,
				height : 50,
				src : 'images/headerImg.jpg'
			});

		 var window = Ext.create('widget.window', {
            width: 850,
            height: 550,
            resizable: false,
			renderTo: Ext.getBody(),
            closable: false,
            title: "Admin Easy Framework ",

            items: [changingImage, {
                xtype: 'component',
                width: 50,
                height: 50,
                autoEl: {
                    tag: 'a',
                    href: 'logout.jsp',
                    html: '<img src="images/logout.png" />'
                }
            }, tabHeader]
        });
        window.show();
		});