import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Status } from './status.model';
import { StatusService } from './status.service';

@Component({
    selector: 'jhi-status-detail',
    templateUrl: './status-detail.component.html'
})
export class StatusDetailComponent implements OnInit, OnDestroy {

    status: Status;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private statusService: StatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInStatuses();
    }

    load(id) {
        this.statusService.find(id).subscribe((status) => {
            this.status = status;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInStatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'statusListModification',
            (response) => this.load(this.status.id)
        );
    }
}
