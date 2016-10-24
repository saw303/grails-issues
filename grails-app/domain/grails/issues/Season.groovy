package grails.issues

class Season {

    Date validFrom
    Date validTo
    String hyperlink

    static constraints = {
        validFrom nullable: false
        validTo nullable: false, validator: { val, obj -> val >= obj.validFrom }
        hyperlink nullable: true, url: true
    }

    @Override
    String toString() {
        "${validFrom?.format('dd.MM.yyyy')} - ${validTo?.format('dd.MM.yyyy')} (id: ${id})"
    }

    def beforeInsert() {
        validFrom = validFrom.clearTime()
        validTo = validTo.clearTime()
    }

    def beforeUpdate() {
        validFrom = validFrom.clearTime()
        validTo = validTo.clearTime()
    }

    static Season currentSeason() {
        return of(new Date())
    }

    static Season of(Date date) {
        def d = date.clearTime()
        return Season.findByValidFromLessThanEqualsAndValidToGreaterThanEquals(d, d)
    }

    static List<Season> between(Date from, Date to) {

        Date a = from.clearTime()
        Date b = to.clearTime()

        if (a == b) {
            return [Season.of(a)]
        } else {
            return Season.executeQuery('''FROM Season s
WHERE (s.validFrom >= :from AND s.validTo <= :to)
OR (s.validFrom > :from AND s.validTo < :to)
OR (s.validFrom > :from AND s.validTo <= :to)
''', [from: a, to: b])
        }
    }
}
