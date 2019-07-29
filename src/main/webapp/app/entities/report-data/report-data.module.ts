import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ResearchDemoSharedModule } from 'app/shared';
import {
    ReportDataComponent,
    ReportDataDetailComponent,
    ReportDataUpdateComponent,
    ReportDataDeletePopupComponent,
    ReportDataDeleteDialogComponent,
    reportDataRoute,
    reportDataPopupRoute
} from './';

const ENTITY_STATES = [...reportDataRoute, ...reportDataPopupRoute];

@NgModule({
    imports: [ResearchDemoSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ReportDataComponent,
        ReportDataDetailComponent,
        ReportDataUpdateComponent,
        ReportDataDeleteDialogComponent,
        ReportDataDeletePopupComponent
    ],
    entryComponents: [ReportDataComponent, ReportDataUpdateComponent, ReportDataDeleteDialogComponent, ReportDataDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ResearchDemoReportDataModule {}
