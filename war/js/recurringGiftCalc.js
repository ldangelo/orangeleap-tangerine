Ext.namespace('RecurringGift');

Ext.onReady(function(){

    RecurringGift.calc = new RecurringGift.GiftCalculator();

    var total = RecurringGift.calc.getTotal();

    if(total > 0) {
        Ext.fly('totalContribution').update(total);
        Ext.fly('totalContributionInfo').setVisible(true,false);
    } else {
        Ext.fly('totalContributionInfo').setVisible(false,false);
    }


    Ext.fly('amountPerGift').on('change', RecurringGift.calculateTotals );
    Ext.fly('frequency').on('change', RecurringGift.calculateTotals );
    Ext.fly('startDate').on('change', RecurringGift.calculateTotals );
    Ext.fly('endDate').on('change', RecurringGift.calculateTotals );

});



RecurringGift.GiftCalculator = function() {

    //PUBLIC: calcuates the total give amount
    this.getTotal = function() {
        var fields = getFieldValues();

        if(fields.total === 0 || !fields.end) {
            return 0.00;
        }

        var span = interval[fields.frequency];
        // always assume at least one interval, even if calculated period is zero
        var period = Math.floor(fields.diff/span) + 1;
        return (period * fields.total).toFixed(2);
    };

    // PRIVATE: returns an object with the value of the form fields we need for the calc
    function getFieldValues() {

        var fields = {};
        var f = Ext.fly('amountPerGift').getValue(true);
        if(isNaN(f)) {
            fields.total = 0.00;
        } else {
            fields.total = f.toFixed(2);
        }
                
        fields.frequency = Ext.fly('frequency').getValue().toLowerCase();
        fields.start = Date.parseDate( Ext.fly('startDate').getValue(), 'm/d/Y').clearTime();

        f = Ext.fly('endDate').getValue();

        if(f) {
            fields.end = Date.parseDate( f, 'm/d/Y').clearTime();
            fields.diff = ((fields.end.getTime() - fields.start.getTime()) / (1000*60*60*24)).toFixed();
            Ext.fly('totalContributionInfo').setVisible(true,false);
        } else {
            Ext.fly('totalContributionInfo').setVisible(false,false);
        }

        return fields;

    }

    // PRIVATE: map of time span labels to number of days
    var interval = {'weekly': 7, 'twice monthly': 14, 'monthly': 28, 'quarterly': 90,
        'twice annually': 180, 'annually': 365};
};


RecurringGift.calculateTotals = function() {
    var total = RecurringGift.calc.getTotal();
    if(total === 0) {
        
    }
    Ext.fly('totalContribution').update(total);
};