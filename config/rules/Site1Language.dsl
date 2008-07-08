[condition][]A gift has been donated=$gift : Gift()
[consequence][]Find the person who donated that gift=PersonService personService = (PersonService)applicationContext.getBean("personService"); Person person = personService.readPersonById($gift.getPerson().getId()); insert(person);
[consequence][]Write "{x}"=System.out.println("{x}");
[condition][]The person who donated that gift has been found=$person : Person()
[consequence][]Setup the person donation object=insert(new DummyPersonDonation(totalAmount, $gift.getPerson().getId()));
[condition][]The person donation object exists=$pdo : DummyPersonDonation()
[consequence][]Flag the person as a major donor=PersonService personService = (PersonService)applicationContext.getBean("personService"); Person person = personService.readPersonById($gift.getPerson().getId()); person.setMajorDonor(true); personService.maintainPerson(person);
[consequence][]Get the dollar value of the persons total donations over the past {number} "{timeUnit}"=GiftService giftService = (GiftService)applicationContext.getBean("giftService"); double totalAmount = $gift.getValue().doubleValue() + giftService.analyzeMajorDonor($gift.getPerson().getId(), getBeginDate({number}, "{timeUnit}"), getCurrentDate()); System.out.println(totalAmount);
[condition][]The person has donated at least {amount} dollars over the time period=DummyPersonDonation(donationAmount >= {amount})
[condition][]of at least {amount} dollars=Gift($gift.value >= {amount})
[consequence][]Get the persons total donations over the year to date=GiftService giftService = (GiftService)applicationContext.getBean("giftService"); double totalAmount = $gift.getValue().doubleValue() + giftService.analyzeMajorDonor($gift.getPerson().getId(), getBeginningOfYearDate(), getCurrentDate());  System.out.println(totalAmount);
[consequence][]Flag the person as a lapsed donor=
[condition][]There is a person=$person : Person($personId = id)
[consequence][]Flag the people who have not made a donation in the past {number} "{timeUnit}"=System.out.println(getBeginDate({number}, "{timeUnit}") + " CURRENT: " +  getCurrentDate()); PersonService personService = (PersonService)applicationContext.getBean("personService"); personService.analyzeLapsedDonor(getBeginDate({number}, "{timeUnit}"), getCurrentDate()); 
[condition][]Scheduled maintenance needs to be done=ScheduledMaintenance()
[*][]Update the person donation object=update($pdo);
[condition][]- to analyze for lapsed donors=analyzeLapsedDonors == true
