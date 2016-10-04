package grails.issues

import grails.test.hibernate.HibernateSpec
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
class SeasonWithoutTestForSpec extends HibernateSpec {

    static final String DATE_FORMAT = 'dd.MM.yyyy'

    def setup() {
    }

    def cleanup() {
    }

    @Override
    List<Class> getDomainClasses() {
        return [Season]
    }

    void "the custom validator is not executed (bug)"() {

        setup:
        def start = new Date()
        def end = start - 1

        when: 'using an incorrect time period'
        def season = new Season(validFrom: start, validTo: end)

        then:
        !season.validate()
    }

    @Unroll
    void "Find all season between #aDate and #bDate (can executeQuery)"() {

        given:
        new Season(validFrom: Date.parse(DATE_FORMAT, '01.01.2014'), validTo: Date.parse(DATE_FORMAT, '31.12.2014')).save()
        new Season(validFrom: Date.parse(DATE_FORMAT, '01.01.2015'), validTo: Date.parse(DATE_FORMAT, '31.12.2015')).save()
        new Season(validFrom: Date.parse(DATE_FORMAT, '01.01.2016'), validTo: Date.parse(DATE_FORMAT, '31.12.2016')).save()
        new Season(validFrom: Date.parse(DATE_FORMAT, '01.01.2017'), validTo: Date.parse(DATE_FORMAT, '31.12.2017')).save()

        expect:
        Season.between(aDate, bDate).sort { it.validFrom }.collect { it.validFrom } == ids

        where:
        aDate                                 | bDate                                 || ids
        Date.parse(DATE_FORMAT, '01.01.2013') | Date.parse(DATE_FORMAT, '31.12.2013') || []
        Date.parse(DATE_FORMAT, '01.01.2014') | Date.parse(DATE_FORMAT, '31.12.2014') || [Date.parse(DATE_FORMAT, '01.01.2014')]
        Date.parse(DATE_FORMAT, '01.01.2014') | Date.parse(DATE_FORMAT, '31.12.2015') || [Date.parse(DATE_FORMAT, '01.01.2014'), Date.parse(DATE_FORMAT, '01.01.2015')]
        Date.parse(DATE_FORMAT, '01.01.2014') | Date.parse(DATE_FORMAT, '31.12.2016') || [Date.parse(DATE_FORMAT, '01.01.2014'), Date.parse(DATE_FORMAT, '01.01.2015'), Date.parse(DATE_FORMAT, '01.01.2016')]
        Date.parse(DATE_FORMAT, '01.01.2014') | Date.parse(DATE_FORMAT, '31.12.2017') || [Date.parse(DATE_FORMAT, '01.01.2014'), Date.parse(DATE_FORMAT, '01.01.2015'), Date.parse(DATE_FORMAT, '01.01.2016'), Date.parse(DATE_FORMAT, '01.01.2017')]
        Date.parse(DATE_FORMAT, '01.01.2015') | Date.parse(DATE_FORMAT, '31.12.2017') || [Date.parse(DATE_FORMAT, '01.01.2015'), Date.parse(DATE_FORMAT, '01.01.2016'), Date.parse(DATE_FORMAT, '01.01.2017')]
        Date.parse(DATE_FORMAT, '01.01.2016') | Date.parse(DATE_FORMAT, '31.12.2017') || [Date.parse(DATE_FORMAT, '01.01.2016'), Date.parse(DATE_FORMAT, '01.01.2017')]
        Date.parse(DATE_FORMAT, '01.01.2017') | Date.parse(DATE_FORMAT, '31.12.2017') || [Date.parse(DATE_FORMAT, '01.01.2017')]
        Date.parse(DATE_FORMAT, '31.12.2017') | Date.parse(DATE_FORMAT, '31.12.2017') || [Date.parse(DATE_FORMAT, '01.01.2017')]
    }
}
