Ext.namespace('RecurringGift');

RecurringGift.GiftCalculator = function() {
    //PUBLIC: calcuates the total give amount
    this.getTotal = function() {
        var fields = getFieldValues();

        if (fields.total === 0 || !fields.end) {
            return 0.00;
        }

        var span = interval[fields.frequency];
        if (span == 0) {
            return fields.total;
        }
        // always assume at least one interval, even if calculated period is zero
        var period = Math.floor(fields.diff/span) + 1;
        return (period * fields.total).toFixed(2);
    };

    // PRIVATE: returns an object with the value of the form fields we need for the calc
    function getFieldValues() {

        var fields = {};
        var amtPerGift = Ext.fly('amountPerGift').getValue();
        if (!amtPerGift) {
            amtPerGift = $("#amountPerGift").text();
        }
        var f = parseFloat(amtPerGift);
        if (isNaN(f)) {
            fields.total = 0.00;
        } else {
            fields.total = f.toFixed(2);
        }

        var freq = Ext.fly('frequency').getValue();
        if (!freq) {
            freq = $("#frequency").text();
        }
        fields.frequency = freq.toLowerCase();
        var startDateWrapperElem = Ext.getCmp('startDate-wrapper');
        if (startDateWrapperElem) {
            var thisStartDate = Date.parseDate(startDateWrapperElem.getRawValue(), 'm/d/Y');
        }
        else {
            var thisStartDate = Date.parseDate($("#startDate").text(), 'm / d / Y');
        }
        fields.start = thisStartDate ? thisStartDate.clearTime() : "";

        f = Ext.getCmp('endDate-wrapper').getRawValue();

        if (f) {
            fields.end = Date.parseDate( f, 'm/d/Y').clearTime();
            fields.diff = ((fields.end.getTime() - fields.start.getTime()) / (1000*60*60*24)).toFixed();
            Ext.fly('totalContributionInfo').setVisible(true,false);
        }
        else {
            Ext.fly('totalContributionInfo').setVisible(false,false);
        }

        return fields;
    }
    // PRIVATE: map of time span labels to number of days
    var interval = {'none': 0, 'weekly': 7, 'twice monthly': 14, 'monthly': 30, 'quarterly': 90,
        'twice annually': 183, 'annually': 365};
};

RecurringGift.calculateTotals = function() {
    var total = RecurringGift.calc.getTotal();
    Ext.fly('totalContribution').update(total);
};

Ext.onReady(function() {
    RecurringGift.calc = new RecurringGift.GiftCalculator();

    var total = RecurringGift.calc.getTotal();

    if (total > 0) {
        Ext.fly('totalContribution').update(total);
        Ext.fly('totalContributionInfo').setVisible(true, false);
    }
    else {
        Ext.fly('totalContributionInfo').setVisible(false, false);
    }

    var amtPerGiftElem = Ext.fly('amountPerGift');
    if (amtPerGiftElem) {
        amtPerGiftElem.on('change', RecurringGift.calculateTotals );
    }
    var freqElem = Ext.fly('frequency');
    if (freqElem) {
        freqElem.on('change', RecurringGift.calculateTotals );
    }

    var startDateElem = Ext.getCmp('startDate-wrapper');
    if (startDateElem) {
        startDateElem.on('change', RecurringGift.calculateTotals );
    }
    var endDateElem = Ext.getCmp('endDate-wrapper');
    if (endDateElem) {
        endDateElem.on('change', RecurringGift.calculateTotals );
    }
});

