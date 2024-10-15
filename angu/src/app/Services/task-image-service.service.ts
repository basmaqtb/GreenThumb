import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TaskImageService {
  private images = [
    'assets/images/task1.webp',
    'assets/images/task2.webp',
    'assets/images/task3.webp',
    'assets/images/task4.webp',
    'assets/images/task5.webp',
  ];

  getImageForTask(taskId: number): string {
    return this.images[taskId % this.images.length];
  }
}