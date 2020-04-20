import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { getTestBed, TestBed } from '@angular/core/testing';
import { UserProfileService } from 'app/entities/user-profile/user-profile.service';

describe('Service Tests', () => {
  describe('UserProfile Service', () => {
    let injector: TestBed;
    let service: UserProfileService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
