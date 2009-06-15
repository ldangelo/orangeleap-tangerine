[condition][com.orangeleap.tangerine.domain.Gift]There is a gift=$gift : Gift($giftId : id)
[consequence][]Write "{x}"=System.out.println("{x}");
[consequence][]Set the constituent as a major donor=$constituent.setMajorDonor(true);
[condition][com.orangeleap.tangerine.domain.Constituent]There is a constituent=$constituent : Constituent($constituentId : id)
[condition][]who has donated {number} gifts=$gifts : ArrayList(size=={number}) from collect ($gift : Gift(value > 0, constituent.id == $constituentId))
[condition][]who has donated at least {totalDonationAmount} dollars over the past {timeAmount} "{timeUnit}"=Number(doubleValue >= {totalDonationAmount}) from accumulate($gift : Gift($value : value, constituent.id == $constituentId, (transactionDate ==null ||transactionDate >= (getBeginDate({timeAmount},"{timeUnit}")))), sum($value))
[condition][]who has made a one time donation of at least {amount} dollars=$gift : Gift(constituent.id == $constituentId, value >= {amount}, transactionDate == null)
[condition][]who has donated at least {totalDonationAmount} dollars over the year to date=Number(doubleValue >= {totalDonationAmount}) from accumulate($gift : Gift($value : value, constituent.id == $constituentId, (transactionDate ==null ||transactionDate >= (getBeginningOfYearDate()))), sum($value))
[condition][]who has not made a donation in the past {timeAmount} "{timeUnit}"=$gifts : ArrayList(size == 0) from collect($gift : Gift(constituent.id == $constituent.id, transactionDate >= (getBeginDate({timeAmount}, "{timeUnit}"))))
[consequence][]Set the constituent as a lapsed donor=ConstituentService constituentService = (ConstituentService)applicationContext.getBean("constituentService"); constituentService.setLapsedDonor($constituent.getId());
[condition][]who has not donated at least {totalDonationAmount} dollars over the past {timeAmount} "{timeUnit}"=Number(doubleValue < {totalDonationAmount}) from accumulate($gift : Gift($value : value, constituent.id == $constituentId, (transactionDate == null || transactionDate >= (getBeginDate({timeAmount}, "{timeUnit}")))), sum($value))
[consequence][]Unset the constituent as a major donor=$constituent.setMajorDonor(false);
[condition][com.orangeleap.tangerine.domain.Constituent]- who is a lapsed donor=lapsedDonor == true
[condition][com.orangeleap.tangerine.domain.Gift]who just donated a gift=$gift : Gift(constituent.id == $constituentId, transactionDate == null)
[consequence][]Unset the constituent as a lapsed donor=$constituent.setLapsedDonor(false);
[condition][com.orangeleap.tangerine.domain.Gift]- with a positive value=value > 0
[condition][com.orangeleap.tangerine.domain.Constituent]- who is a major donor=majorDonor == true
[condition][com.orangeleap.tangerine.domain.Constituent]- who is not a major donor=majorDonor == false
[condition][]who has donated in {x} out of the last {y} months=$gifts : ArrayList(size >= {x}) from collect(Gift(constituent.id == $constituentId)) eval(analyzeMonthlyDonor($gifts, {x}, {y}))
[condition][]who donated at least {number} gifts=$totalGifts : ArrayList(size >= 1) from collect (Gift(value > 0, constituent.id == $constituentId))
