import { Form, FormControl, ValidationErrors } from "@angular/forms";

export class BeValidate {
    //whitespace validation
    static allWhitespaceValidator(control:FormControl) :null | ValidationErrors {
        //kiem tra xem control co gia tri la toan khoang trang hay khong
        if((control.value != null) && (control.value.trim().length === 0)) {
            //khong hop le, tra ve loi
            return { 'allwhitespace': true };
        } else {
            //hop le, tra ve null
            return null;
        }
    }
}

