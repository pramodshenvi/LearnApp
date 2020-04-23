import { NgModule } from '@angular/core';
import { LearnAppSharedLibsModule } from './shared-libs.module';
import { AlertComponent } from './alert/alert.component';
import { AlertErrorComponent } from './alert/alert-error.component';
import { LoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

import {JhMaterialModule} from 'app/shared/jh-material.module';
import { DynamicDropdownComponent } from 'app/shared/util/dynamic-dropdown';

@NgModule({
  imports: [JhMaterialModule, LearnAppSharedLibsModule],
  declarations: [AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, DynamicDropdownComponent],
  entryComponents: [LoginModalComponent],
  exports: [JhMaterialModule, LearnAppSharedLibsModule, AlertComponent, AlertErrorComponent, LoginModalComponent, HasAnyAuthorityDirective, DynamicDropdownComponent]
})
export class LearnAppSharedModule {}
