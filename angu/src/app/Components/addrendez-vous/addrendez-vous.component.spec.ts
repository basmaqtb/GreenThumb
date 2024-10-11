import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddrendezVousComponent } from './addrendez-vous.component';

describe('AddrendezVousComponent', () => {
  let component: AddrendezVousComponent;
  let fixture: ComponentFixture<AddrendezVousComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddrendezVousComponent]
    });
    fixture = TestBed.createComponent(AddrendezVousComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
