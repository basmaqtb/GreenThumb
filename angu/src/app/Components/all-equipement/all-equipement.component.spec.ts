import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllEquipementComponent } from './all-equipement.component';

describe('AllEquipementComponent', () => {
  let component: AllEquipementComponent;
  let fixture: ComponentFixture<AllEquipementComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AllEquipementComponent]
    });
    fixture = TestBed.createComponent(AllEquipementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
