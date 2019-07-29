import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReportData } from 'app/shared/model/report-data.model';
import { ReportDataService } from './report-data.service';

@Component({
    selector: 'jhi-report-data-delete-dialog',
    templateUrl: './report-data-delete-dialog.component.html'
})
export class ReportDataDeleteDialogComponent {
    reportData: IReportData;

    constructor(
        protected reportDataService: ReportDataService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reportDataService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reportDataListModification',
                content: 'Deleted an reportData'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-report-data-delete-popup',
    template: ''
})
export class ReportDataDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reportData }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReportDataDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.reportData = reportData;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/report-data', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/report-data', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
