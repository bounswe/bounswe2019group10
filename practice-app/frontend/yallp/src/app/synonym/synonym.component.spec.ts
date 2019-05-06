import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SynonymComponent } from './synonym.component';

describe('SynonymComponent', () => {
  let component: SynonymComponent;
  let fixture: ComponentFixture<SynonymComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SynonymComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SynonymComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
