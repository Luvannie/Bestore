import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { complain } from '../../common/model/complain';
import { DOCUMENT } from '@angular/common';
import { TokenService } from '../../service/token.service';
import { ComplainService } from '../../service/complain.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-complain',
  templateUrl: './complain.component.html',
  styleUrls: ['./complain.component.css']
})
export class ComplainComponent implements OnInit {
  complainFormGroup!: FormGroup;
  localStorage?: Storage;
  complain: complain = {
    user_id: 0,
    order_id: 0,
    complain: ''
  };
  token: string = '';
  user_id: number = 0;

  constructor(
    private tokenService: TokenService,
    private complainService: ComplainService,
    private formBuilder: FormBuilder,
    private router: Router,
    @Inject(DOCUMENT) private document: Document
  ) {
    this.localStorage = document.defaultView?.localStorage;
  }

  ngOnInit(): void {
    this.complainFormGroup = this.formBuilder.group(
      {
        complain_content: new FormControl('', [Validators.required, Validators.minLength(6)])
      }
    )
    this.token = this.tokenService.getToken();
    this.user_id = this.tokenService.getUserId();
    this.complain.user_id = this.user_id;
    let orderId = this.localStorage?.getItem('complain_order_id');
    this.complain.order_id = orderId ? Number(orderId) : 0;
  }

  onSubmit(): void {
    const complain_content = this.complainFormGroup.get('complain_content')?.value;
    this.complain.complain = complain_content;
    // console.log(this.complain);
    console.log(this.token);
    this.complainService.createComplain(this.token, this.complain).subscribe({
      next: (apiResponse) => {
        debugger
        console.log(apiResponse);
      },
      complete: () => {
        debugger
        console.log('Complete');
        this.router.navigateByUrl('/');

      },
      error: (error) => {
        debugger
        console.log(error);
      }
    });
  }

  get complain_content(){
    return this.complainFormGroup.get('complain_content');
  }
}