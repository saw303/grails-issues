package grails.issues

import grails.transaction.Transactional

@Transactional
class SeasonService {

    @Transactional(readOnly = true)
    List<Season> findAll() {
        Season.list()
    }
}
