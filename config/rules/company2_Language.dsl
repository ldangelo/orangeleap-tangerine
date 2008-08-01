[condition][com.mpower.domain.Gift]There is a gift=$gift : Gift($giftId : id)
[consequence][]Write "{x}"=System.out.println("{x}");
[consequence][]Set the person as a major donor=$person.setMajorDonor(true);
[condition][com.mpower.domain.Person]There is a person=$person : Person($personId : id)
[condition][]who has donated {number} gifts=$gifts : ArrayList(size=={number}) from collect ($gift : Gift(value > 0, person.id == $personId))
[condition][]who has donated at least {totalDonationAmount} dollars over the past {timeAmount} "{timeUnit}"=Number(doubleValue >= {totalDonationAmount}) from accumulate($gift : Gift($value : value, person.id == $personId, (transactionDate ==null ||transactionDate >= (getBeginDate({timeAmount},"{timeUnit}")))), sum($value))
[condition][]who has made a one time donation of at least {amount} dollars=$gift : Gift(person.id == $personId, value >= {amount}, transactionDate == null)
[condition][]who has donated at least {totalDonationAmount} dollars over the year to date=Number(doubleValue >= {totalDonationAmount}) from accumulate($gift : Gift($value : value, person.id == $personId, (transactionDate ==null ||transactionDate >= (getBeginningOfYearDate()))), sum($value))
[condition][]who has not made a donation in the past {timeAmount} "{timeUnit}"=$gifts : ArrayList(size == 0) from collect($gift : Gift(person.id == $person.id, transactionDate >= (getBeginDate({timeAmount}, "{timeUnit}"))))
[consequence][]Set the person as a lapsed donor=PersonService personService = (PersonService)applicationContext.getBean("personService"); personService.setLapsedDonor($person.getId());
[condition][]who has not donated at least {totalDonationAmount} dollars over the past {timeAmount} "{timeUnit}"=Number(doubleValue < {totalDonationAmount}) from accumulate($gift : Gift($value : value, person.id == $personId, (transactionDate == null || transactionDate >= (getBeginDate({timeAmount}, "{timeUnit}")))), sum($value))
[consequence][]Unset the person as a major donor=$person.setMajorDonor(false);
[condition][com.mpower.domain.Person]- who is a lapsed donor=lapsedDonor == true
[condition][com.mpower.domain.Gift]who just donated a gift=$gift : Gift(person.id == $personId, transactionDate == null)
[consequence][]Unset the person as a lapsed donor=$person.setLapsedDonor(false);
[condition][com.mpower.domain.Gift]- with a positive value=value > 0
[condition][com.mpower.domain.Person]- who is a major donor=majorDonor == true
[condition][com.mpower.domain.Person]- who is not a major donor=majorDonor == false
[condition][]who has donated in {x} out of the last {y} months=$gifts : ArrayList(size >= {x}) from collect(Gift(person.id == $personId)) eval(analyzeMonthlyDonor($gifts, {x}, {y}))
[condition][]who has donated al {number} gifts=$totalGifts : ArrayList(size >= {number}) from collect (Gift(value > 0, person.id == $personId))
