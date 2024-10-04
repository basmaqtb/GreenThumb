import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllRendezvousComponent } from './all-rendezvous.component';

describe('AllRendezvousComponent', () => {
  let component: AllRendezvousComponent;
  let fixture: ComponentFixture<AllRendezvousComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AllRendezvousComponent]
    });
    fixture = TestBed.createComponent(AllRendezvousComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
