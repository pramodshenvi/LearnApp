import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormBuilder } from '@angular/forms';
import { UserProfileUpdateComponent } from 'app/entities/user-profile/user-profile-update.component';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';
import { LearnAppTestModule } from '../../../test.module';

describe('Component Tests', () => {
  describe('UserProfile Management Update Component', () => {
    let comp: UserProfileUpdateComponent;
    let fixture: ComponentFixture<UserProfileUpdateComponent>;
    let service: UserProfileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LearnAppTestModule],
        declarations: [UserProfileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(UserProfileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserProfileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserProfileService);
    });
  });
});
