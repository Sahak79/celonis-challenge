import {Component, OnInit} from '@angular/core';

import {AppDataService} from '../services/app-data.service';
import {Task} from "../model/task";
import {NgForm} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {
  tasks: Array<Task> = Array();
  model: Task = new Task();
  edit_model: Task = new Task();
  loading = false;
  error = '';
  success = '';

  constructor(private appDataService: AppDataService,
              private router: Router) {
  }

  ngOnInit() {
    this.tasks = this.appDataService.getTasks();
  }

  addTask(form: NgForm) {
    this.loading = true;
    this.appDataService.addTask(this.model)
      .subscribe(
        result => {
          this.loading = false;

          if (result) {
            this.tasks.push(result);
            form.resetForm();
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

  deleteTask(task: Task) {
    this.loading = true;
    this.appDataService.deleteTask(task)
      .subscribe(result => {
        this.loading = false;
        this.tasks.splice(this.tasks.indexOf(task), 1);
      });
  }

  openTask(id: number){
    this.router.navigate(['user/task/'+id]);
  }

  openMakeTask(){
    this.router.navigate(['user/make/task']);
  }

}
