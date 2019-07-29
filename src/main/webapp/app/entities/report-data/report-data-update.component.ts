import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiDataUtils } from 'ng-jhipster';
import { IReportData } from 'app/shared/model/report-data.model';
import { ReportDataService } from './report-data.service';

@Component({
    selector: 'jhi-report-data-update',
    templateUrl: './report-data-update.component.html'
})
export class ReportDataUpdateComponent implements OnInit {
    reportData: IReportData;
    isSaving: boolean;

    constructor(
        protected dataUtils: JhiDataUtils,
        protected reportDataService: ReportDataService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
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

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reportData.id !== undefined) {
            this.subscribeToSaveResponse(this.reportDataService.update(this.reportData));
        } else {
            this.subscribeToSaveResponse(this.reportDataService.create(this.reportData));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReportData>>) {
        result.subscribe((res: HttpResponse<IReportData>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
