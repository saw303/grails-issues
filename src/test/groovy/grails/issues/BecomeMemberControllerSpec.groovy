package grails.issues

import grails.test.hibernate.HibernateSpec
import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(BecomeMemberController)
class BecomeMemberControllerSpec extends HibernateSpec {

    static final String DATE_FORMAT = 'dd.MM.yyyy'

    def cleanup() {
        BecomeMember.findAll()*.delete(flush: true)
    }

    @Override
    List<Class> getDomainClasses() {
        [
                BecomeMember
        ]
    }

    void "Apply for a new membership with one season ticket"() {

        given:
        BecomeMember member = new BecomeMember(
                firstname: 'Peter',
                lastname: 'Röesli',
                street: 'Eine Strasse',
                housenumber: '12',
                postcode: '8152',
                city: 'Opfikon',
                email: 's@b.ch',
                phone: '62736',
                mobile: '6767678',
                birthday: new Date()
        )

        and:
        params['rang-1'] = 'RANG1'
        params['name-1'] = 'Peter Röesli'
        params['dob-1'] = '01.11.1978'

        params['rang-4'] = '-'
        params['name-4'] = ''
        params['dob-4'] = ''

        params['rang-3'] = '-'
        params['name-3'] = ''
        params['dob-3'] = ''

        params['rang-4'] = '-'
        params['name-4'] = ''
        params['dob-4'] = ''

        and:
        request.method = 'POST'

        and:
        def memberService = Mock(MemberService)
        controller.memberService = memberService

        when:
        controller.save(member)

        then:
        BecomeMember.count() == 1

        and:
        def potentialMember = BecomeMember.findAll()[0]

        potentialMember.firstname == 'Peter'
        potentialMember.lastname == 'Röesli'
        potentialMember.street == 'Eine Strasse'
        potentialMember.housenumber == '12'
        potentialMember.postcode == '8152'
        potentialMember.city == 'Opfikon'
        potentialMember.birthday.format(DATE_FORMAT) == new Date().format(DATE_FORMAT)
        potentialMember.email == 's@b.ch'
        potentialMember.phone == '62736'
        potentialMember.mobile == '6767678'
        !potentialMember.officePhone
        !potentialMember.addressLine1
        !potentialMember.addressLine2
        !potentialMember.addressLine3
        !potentialMember.addressLine4
        !potentialMember.addressLine5

        and:
        potentialMember.tickets.size() == 1
        potentialMember.tickets[0].cardholder == 'Peter Röesli'
        potentialMember.tickets[0].pricingCategory == 'RANG1'
        potentialMember.tickets[0].dob.format(DATE_FORMAT) == '01.11.1978'

        and:
        1 * memberService.createTasks('Hello World')
    }

    void "Apply for a new membership with two season ticket"() {

        given:
        BecomeMember member = new BecomeMember(
                firstname: 'Susanne',
                lastname: 'Geissler',
                street: 'Strasse',
                housenumber: '2',
                postcode: '8003',
                city: 'Zürich',
                email: 'uno@due.ch',
                phone: '777',
                mobile: '888',
                officePhone: '999',
                birthday: new Date()
        )

        and:
        params['rang-1'] = 'RANG3'
        params['name-1'] = 'Susanne Geissler'
        params['dob-1'] = member.birthday.format(DATE_FORMAT)

        params['rang-2'] = 'RANG2'
        params['name-2'] = 'Stefan Saubermann'
        params['dob-2'] = '01.01.1923'

        params['rang-3'] = '-'
        params['name-3'] = ''
        params['dob-3'] = ''

        params['rang-4'] = '-'
        params['name-4'] = ''
        params['dob-4'] = ''

        and:
        request.method = 'POST'

        and:
        def memberService = Mock(MemberService)
        controller.memberService = memberService

        when:
        controller.save(member)

        then:
        BecomeMember.count() == 1

        and:
        def potentialMember = BecomeMember.findAll()[0]

        potentialMember.firstname == 'Susanne'
        potentialMember.lastname == 'Geissler'
        potentialMember.street == 'Strasse'
        potentialMember.housenumber == '2'
        potentialMember.postcode == '8003'
        potentialMember.city == 'Zürich'
        potentialMember.birthday.format(DATE_FORMAT) == new Date().format(DATE_FORMAT)
        potentialMember.email == 'uno@due.ch'
        potentialMember.phone == '777'
        potentialMember.mobile == '888'
        potentialMember.officePhone == '999'
        !potentialMember.addressLine1
        !potentialMember.addressLine2
        !potentialMember.addressLine3
        !potentialMember.addressLine4
        !potentialMember.addressLine5

        and:
        potentialMember.tickets.size() == 2

        potentialMember.tickets.find {
            it.cardholder == 'Susanne Geissler'
        }.pricingCategory == PricingCategory.RANG3
        potentialMember.tickets.find {
            it.cardholder == 'Susanne Geissler'
        }.dob.format(DATE_FORMAT) == member.birthday.format(DATE_FORMAT)

        potentialMember.tickets.find {
            it.cardholder == 'Stefan Saubermann'
        }.pricingCategory == PricingCategory.RANG2
        potentialMember.tickets.find {
            it.cardholder == 'Stefan Saubermann'
        }.dob.format(DATE_FORMAT) == '01.01.1923'

        and:
        1 * memberService.createTasks('Hello World')
    }
}
