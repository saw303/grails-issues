package grails.issues

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

@Transactional(readOnly = true)
class BecomeMemberController {

    static allowedMethods = [save: "POST"]

    MemberService memberService

    @Transactional
    def save(BecomeMember becomeMemberInstance) {

        memberService.createTasks('Hello World')

        request.withFormat {
            form multipartForm {
                respond becomeMemberInstance, view: 'done'
            }
            '*' { respond becomeMemberInstance, [status: CREATED] }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'becomeMember.label', default: 'BecomeMember'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
