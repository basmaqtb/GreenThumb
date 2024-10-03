import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEquipementComponent } from './add-equipement.component';

describe('AddEquipementComponent', () => {
  let component: AddEquipementComponent;
  let fixture: ComponentFixture<AddEquipementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddEquipementComponent]
    });
    fixture = TestBed.createComponent(AddEquipementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
