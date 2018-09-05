import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { StatusComponent } from './status.component';
import { StatusDetailComponent } from './status-detail.component';
import { StatusPopupComponent } from './status-dialog.component';
import { StatusDeletePopupComponent } from './status-delete-dialog.component';

export const statusRoute: Routes = [
    {
        path: 'status',
        component: StatusComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Statuses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'status/:id',
        component: StatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Statuses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const statusPopupRoute: Routes = [
    {
        path: 'status-new',
        component: StatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Statuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'status/:id/edit',
        component: StatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Statuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'status/:id/delete',
        component: StatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Statuses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
