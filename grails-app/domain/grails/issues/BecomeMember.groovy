package grails.issues

import grails.databinding.BindingFormat

class BecomeMember {

    String firstname
    String lastname
    String street
    String housenumber
    String postcode
    String city
    @BindingFormat('dd.MM.yyyy')
    Date birthday
    String email
    String phone
    String mobile
    String officePhone

    String addressLine1
    String addressLine2
    String addressLine3
    String addressLine4
    String addressLine5

    Date applicationDate = new Date()
    BecomeMemberStatus status = BecomeMemberStatus.NEW

    static constraints = {
        firstname blank: false, nullable: false, maxSize: 50
        lastname blank: false, nullable: false, maxSize: 50
        street blank: false, maxSize: 150, nullable: false
        housenumber nu: false, maxSize: 6, nullable: false
        postcode blank: false, maxSize: 10, nullable: false
        city blank: false, maxSize: 60, nullable: false
        email email: true, nullable: false
        phone blank: false, nullable: false, maxSize: 20
        mobile blank: false, nullable: false, maxSize: 20
        officePhone blank: true, nullable: true, maxSize: 20
        birthday nullable: false, validator: { val ->
            Date min = Date.parse('dd.MM.yyyy', '01.01.1900')
            if (! val.after(min)) return ['tooOld']
        }
        addressLine1 nullable: true, maxSize: 50
        addressLine2 nullable: true, maxSize: 50
        addressLine3 nullable: true, maxSize: 50
        addressLine4 nullable: true, maxSize: 50
        addressLine5 nullable: true, maxSize: 50
        applicationDate nullable: false
        status nullable: false
    }
}

enum BecomeMemberStatus {
    NEW, DECLINED, APPROVED
}
