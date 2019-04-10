import { NgModule } from '@angular/core';

import { ResearchDemoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [ResearchDemoSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [ResearchDemoSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class ResearchDemoSharedCommonModule {}
