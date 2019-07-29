import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'person',
                loadChildren: './person/person.module#ResearchDemoPersonModule'
            },
            {
                path: 'report-data',
                loadChildren: './report-data/report-data.module#ResearchDemoReportDataModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResearchDemoEntityModule {}
