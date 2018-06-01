import {Injectable} from '@angular/core';
import {AuthHttp} from 'angular2-jwt';
import {Task} from '../model/task';

@Injectable()
export class AppDataService {
  private data: any;
  constructor(private http: AuthHttp) {
  }

  addTask(task: Task) {
    return this.http.post('/springjwt/tasks', task).map(res => res.json());
  }

  editTask(task: Task) {
    return this.http.put('/springjwt/tasks/'+task.id, task).map(res => res.json());
  }

  deleteTask(task: Task) {
    return this.http.delete('/springjwt/tasks/' + task.id).map(res => res.json());
  }

  cancelTask(task: Task) {
    return this.http.get('/springjwt/tasks/' + task.id + "/cancel").map(res => res.json());
  }

  getTasks() {
    this.data = [];
    this.http.get('/springjwt/tasks').subscribe(res => {
      this.data.push(...res.json());
    });
    return this.data;
  }

  getTypes() {
    this.data = [];
    this.http.get('/springjwt/types').subscribe(res => {
      this.data.push(...res.json());
    });
    return this.data;
  }

  getTask(id: number) {
    return this.http.get('/springjwt/tasks/'+id);
  }

  getUsers() {
    return this.http.get('/springjwt/users').map(res => res.json());
  }
}
