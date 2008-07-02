[condition][]A donation has been made=$gift : Gift()
[consequence][]Find the person who donated that gift=PersonService personService = (PersonService)applicationContext.getBean("personService"); Person person = personService.readPersonById($gift.getPerson().getId()); insert(person);
[consequence][]Write "{x}"=System.out.println("{x}");
[condition][]The person who made that donation has been found=$person : Person()
[consequence][]Setup person donation object=insert(new DummyPersonDonation(totalAmount, $gift.getPerson().getId()));
[condition][]Person donation object exists=$pdo : DummyPersonDonation()
[consequence][]Flag the person as a major donor=PersonService personService = (PersonService)applicationContext.getBean("personService"); Person person = personService.readPersonById($gift.getPerson().getId()); person.setMajorDonor(true); personService.maintainPerson(person);
[consequence][]Get the persons total donations over the past {number} "{timeUnit}"=GiftService giftService = (GiftService)applicationContext.getBean("giftService"); double totalAmount = $gift.getValue().doubleValue() + giftService.analyzeMajorDonor($gift.getPerson().getId(), getBeginDate({number}, "{timeUnit}"), getCurrentDate()); System.out.println(totalAmount);
[condition][]The person has donated at least {amount} dollars over the time period=DummyPersonDonation(donationAmount >= {amount})
[condition][]of at least {amount} dollars=Gift($gift.value >= {amount})
[consequence][]Get the persons total donations over the year to date=GiftService giftService = (GiftService)applicationContext.getBean("giftService"); double totalAmount = $gift.getValue().doubleValue() + giftService.analyzeMajorDonor($gift.getPerson().getId(), getBeginningOfYearDate(), getCurrentDate());
