import {TaskType} from "./task.type";
import {TaskStatus} from "./task.status";

export class Task {
  public id: number;
  public fromValue: number;
  public toValue: number;
  public currentValue: number;
  public status: TaskStatus;
  public creationDate: Date;
  public storageLocation: string;
  public taskType: TaskType;
  constructor() {}
}
