import {Component, OnInit} from '@angular/core';

import {AppDataService} from '../../services/app-data.service';
import {Router} from "@angular/router";
import {TaskType} from "../../model/task.type";
import {Task} from "../../model/task";
import {TaskStatus} from "../../model/task.status";

@Component({
  selector: 'app-user-task-make',
  templateUrl: 'make.component.html',
  styleUrls: ['make.component.css']
})
export class MakeComponent implements OnInit {
  types: Array<TaskType> = Array();
  task: Task = new Task();
  loading = false;
  error = '';
  success = '';

  constructor(private appDataService: AppDataService,
              private router: Router,) {
  }

  ngOnInit() {
    this.types = this.appDataService.getTypes();
  }

  makeTask(id: number){
    this.task.status = new TaskStatus(1, "");
    this.task.fromValue = 0;
    this.task.toValue = 0;
    this.task.currentValue = 0;
    this.task.creationDate = new Date();
    this.task.taskType = new TaskType(id);
    this.task.storageLocation = "disk";

    this.appDataService.addTask(this.task).subscribe(res=>{
      console.log(this.task);
      if (res) {
        this.router.navigate(['user/task/'+res.id]);
      } else {
        this.error = 'Something went wrong';
      }
    });

  }

}
