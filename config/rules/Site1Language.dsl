[condition][]There is an Instance with field of "{value}"=i: Instance(field=="{value}")
[condition][]Instance is at least {number} and field is "{value}"=i: Instance(number > {number}, location=="{value}")
[consequence][]Log : "{message}"=System.out.println("{message}");
[consequence][]Set field of instance to "{value}"=i.setField("{value}");
[consequence][]Create instance : "{value}"=insert(new Instance("{value}"));
[condition][]There is no current Instance with field : "{value}"=not Instance(field == "{value}")
[consequence][]Report error : "{error}"=System.err.println("{error}");
[consequence][]Retract the fact : '{variable}'=retract({variable}); //this would retract bound variable {variable}
[condition][]A donation has been made=$gift : Gift()
[consequence][]Find the person who donated that gift=PersonService personService = (PersonService)applicationContext.getBean("personService"); Person person = personService.readPersonById($gift.getPerson().getId()); insert(person);
[consequence][]Write "{x}"=System.out.println("{x}");
[condition][]The person who made that donation has been found=$person : Person()
[consequence][]Setup person donation object=insert(DummyPersonDonation);
[condition][]Person donation object exists=$pdo : DummyPersonDonation()
[condition][]The person has made a donation of at least "{amount}"=Gift($gift.value >= "{amount}")
[consequence][]Flag the person as a major donor=PersonService personService = (PersonService)applicationContext.getBean("personService"); Person person = personService.readPersonById($gift.getPerson().getId()); person.setMajorDonor(true); personService.maintainPerson(person);
[condition][]The person has made a donation of at least "{amount}" over the past "{number}" "{timeUnit}"=DummyPersonDonation($donationAmount :"{amount}", $number : "{number}", $timeUnit : "{timeUnit}")
