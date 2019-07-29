import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ReportData } from 'app/shared/model/report-data.model';
import { ReportDataService } from './report-data.service';
import { ReportDataComponent } from './report-data.component';
import { ReportDataDetailComponent } from './report-data-detail.component';
import { ReportDataUpdateComponent } from './report-data-update.component';
import { ReportDataDeletePopupComponent } from './report-data-delete-dialog.component';
import { IReportData } from 'app/shared/model/report-data.model';

@Injectable({ providedIn: 'root' })
export class ReportDataResolve implements Resolve<IReportData> {
    constructor(private service: ReportDataService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IReportData> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ReportData>) => response.ok),
                map((reportData: HttpResponse<ReportData>) => reportData.body)
            );
        }
        return of(new ReportData());
    }
}

export const reportDataRoute: Routes = [
    {
        path: '',
        component: ReportDataComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportData'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ReportDataDetailComponent,
        resolve: {
            reportData: ReportDataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportData'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ReportDataUpdateComponent,
        resolve: {
            reportData: ReportDataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportData'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ReportDataUpdateComponent,
        resolve: {
            reportData: ReportDataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportData'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reportDataPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ReportDataDeletePopupComponent,
        resolve: {
            reportData: ReportDataResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ReportData'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
