package grails.issues

import grails.test.hibernate.HibernateSpec
import grails.test.mixin.TestFor

/**
 * @author Silvio Wangler
 */
@TestFor(SeasonService)
class SeasonServiceSpec extends HibernateSpec {

    @Override
    List<Class> getDomainClasses() {
        [Season]
    }

    void "Find all seasons"() {

        when:
        List<Season> seasons = service.findAll()

        then:
        seasons.size() == 0
    }
}
