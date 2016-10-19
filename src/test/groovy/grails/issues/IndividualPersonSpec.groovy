package grails.issues

import grails.test.hibernate.HibernateSpec

class IndividualPersonSpec extends HibernateSpec {

    @Override
    List<Class> getDomainClasses() {
        [Address, IndividualPerson, Season, User, InvoiceAddress]
    }

    void "Validation fails"() {
        given:
        def member = new IndividualPerson(firstname: 'helo', name: 'wangler', memberSince: '2014', active: Boolean.TRUE)

        when:
        def valid = member.validate()

        and:
        member.save()

        then:
        valid

        and:
        member.id
    }
}
