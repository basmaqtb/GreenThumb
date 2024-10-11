import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../../Services/user.service'; // Adjust the path if necessary
import { Utilisateur } from '../../Modules/Utilisateur'; // Adjust the path if necessary

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {
  users: Utilisateur[] = [];
  filteredUsers: Utilisateur[] = [];
  searchTerm: string = '';

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe(
      (data) => {
        this.users = data;
        this.filteredUsers = data; // Initialize filteredUsers with the complete list
      },
      (error) => {
        console.error('Error fetching users', error);
      }
    );
  }

  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      console.log('Deleting user with ID:', id); // Log the ID
      this.userService.deleteUser(id).subscribe(
        () => {
          this.users = this.users.filter(user => user.id !== id);
          this.searchUsers(); // Reapply filter after deletion
        },
        (error) => {
          console.error('Error deleting user', error);
        }
      );
    }
  }

  editUser(id: number): void {
    this.router.navigate(['/users/update', id]); // Adjust the route according to your application
  }

  searchUsers(): void {
    if (this.searchTerm === '') {
      this.filteredUsers = this.users; // Reset to all users if search term is empty
    } else {
      this.filteredUsers = this.users.filter(user =>
        user.fullName.toLowerCase().includes(this.searchTerm.toLowerCase()) // Assuming you want to search by fullname
      );
    }
  }
}
