import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AntonymComponent } from './antonym.component';

describe('AntonymComponent', () => {
  let component: AntonymComponent;
  let fixture: ComponentFixture<AntonymComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AntonymComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AntonymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
