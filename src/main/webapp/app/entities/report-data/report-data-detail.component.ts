import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IReportData } from 'app/shared/model/report-data.model';

@Component({
    selector: 'jhi-report-data-detail',
    templateUrl: './report-data-detail.component.html'
})
export class ReportDataDetailComponent implements OnInit {
    reportData: IReportData;

    constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reportData }) => {
            this.reportData = reportData;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
