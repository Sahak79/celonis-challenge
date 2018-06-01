import {Component, OnInit} from '@angular/core';
import {ActivatedRoute } from '@angular/router';

import {AppDataService} from '../services/app-data.service';
import {Task} from "../model/task";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Rx";

@Component({
  selector: 'app-user-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  task: Task = new Task();

  model: Task = new Task();
  edit_model: Task = new Task();
  loading = false;
  error = '';
  success = '';
  number_pattern : RegExp = /^[0-9]+$/;


  constructor(private appDataService: AppDataService,
              private route: ActivatedRoute,
              private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.appDataService.getTask(params['id']).subscribe(res=>{
        this.task = res.json();
        if(this.task.status.id == 3){
          this.getTaskProgress(params['id']);
        }
      });
    });
  }

  public getTaskProgress(id: number) {
    Observable.interval(1500)
      .switchMap(() => this.appDataService.getTask(id))
      .map((data) => data.json())
      .takeWhile((data) => data.status.id==3)
      .subscribe(
        (data) => {
          this.task.currentValue = data.currentValue;
        },
        (error) => {
          this.error = error
        },
        () => {
          if(this.task.status.id == 3){
            this.task.status.id = 4
          }
        }
      );
  }

  fromValidation() : boolean {
    return  this.number_pattern.test(this.model.fromValue.toString());
  }

  toValidation() : boolean {
    return this.number_pattern.test(this.model.toValue.toString());
  }

  validateFromToRange() : boolean{
    if(this.model.fromValue >= this.model.toValue){
      this.error = "From value should be smaller then to value";
      return false;
    }else{
      this.error = "";
    }
    return true;
  }

  setFromToValues() {
    if(!this.validateFromToRange()){
      return false;
    }
    this.loading = true;
    this.task.fromValue = this.model.fromValue;
    this.task.toValue = this.model.toValue;
    this.task.status.id = 2;
    this.appDataService.editTask(this.task)
      .subscribe(
        result => {
          this.loading = false;
          if (result) {
            this.task.status.id = result.status.id;
          } else {
            this.error = 'Something went wrong';
          }
        },
        error => {
          this.error = 'Something went wrong';
          this.loading = false;
        }
      );
  }

  setExecutingStatus(){
    this.task.status.id = 3;
    this.appDataService.editTask(this.task)
      .subscribe(
        result => {
          this.loading = false;
          if (result && result.status.id == 3) {
            this.router.navigate(['user/task/'+this.task.id]);
            this.getTaskProgress(this.task.id);
          } else {
            this.error = 'Something went wrong';
          }
        },
        error => {
          this.error = 'Something went wrong';
          this.loading = false;
        }
      );
  }

  cancelExecution(){
    this.appDataService.cancelTask(this.task)
      .subscribe(
        result => {
          this.task.status.id = 2;
        }
      );
  }

}
