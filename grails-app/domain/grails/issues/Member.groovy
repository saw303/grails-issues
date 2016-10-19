package grails.issues

import org.hibernate.NonUniqueResultException

abstract class Member {
    String name
    String email
    User user
    String phone
    String memberSince
    InvoiceAddress invoiceAddress
    Boolean active

    static constraints = {
        name nullable: false, maxSize: 50
        email nullable: true, email: true, blank: true
        user nullable: true, unique: true
        phone nullable: true, maxSize: 20
        memberSince nullable: false, blank: false, matches: /\d{4}/
        invoiceAddress nullable: true
        active nullable: false
    }

    static mapping = {
        tablePerHierarchy false
        sort name: 'asc'
    }

    static List<Member> findAllActiveMembers(Map params) {
        Member.findAllByActive(Boolean.TRUE, params)
    }

    Address currentAddress() {

        def date = new Date()
        log.debug "About to find the current address for member ${name} (id: ${id}) that is currently valid (date ${date.format('dd.MM.yyyy')}"

        def query = Address.where {
            validFrom <= date && validTo >= date && member == this
        }

        try {
            return query.find()
        } catch (NonUniqueResultException e) {
            log.error("It seems that there is a problem with the addresses of member (id: '${id}', name: '${completeName()}'", e)
            throw e
        }
    }

    /**
     * This method can be used to get the full name of a member instance
     * @return the complete name of the person
     */
    abstract String completeName()
}
