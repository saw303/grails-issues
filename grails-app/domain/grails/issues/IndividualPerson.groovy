package grails.issues

class IndividualPerson extends Member {

    String firstname
    Date dateOfBirth
    String homePhone
    String mobilePhone

    static constraints = {
        firstname nullable: false, maxSize: 50
        dateOfBirth nullable: true, attributes: [precision: "day"]
        homePhone nullable: true, maxSize: 20
        mobilePhone nullable: true, maxSize: 20
    }


    @Override
    String toString() {
        return completeName()
    }

    @Override
    String completeName() {
        return "${firstname} ${super.name}"
    }
}
