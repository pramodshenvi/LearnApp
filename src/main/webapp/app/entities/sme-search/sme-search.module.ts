import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { LearnAppSharedModule } from 'app/shared/shared.module';
import { SMESearchComponent } from './sme-search.component';
import { smeSearchRoute } from './sme-search.route';

@NgModule({
  imports: [LearnAppSharedModule, RouterModule.forChild(smeSearchRoute)],
  declarations: [
    SMESearchComponent
  ],
  entryComponents: []
})
export class LearnAppSMESearchModule {}
