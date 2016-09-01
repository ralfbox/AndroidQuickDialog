# AndroidQuickDialog
Andorid library to the creation of dialogs. With this library can be created very quickly while keeping the code clean.

## Usage
```java

	public class MainActivity extends AppCompatActivity {
	
		private static final String REQUEST_UNIQUE_TAG = "any-dialog";
	
		...
	
		private void createDialog(){
	//Create dialog
			new QuickDialog.Builder(this, REQUEST_UNIQUE_TAG)
					.title("Any title")
					.message("Message")
					.positiveButton("OK")
					.negativeButton("Cancel")
					.show(getSupportFragmentManager());
		}
		
	//create method for positive button click
		@PositiveButtonQD(REQUEST_UNIQUE_TAG)
		private void onPositiveButtonClick(){
			Toast.makeText(this, "Clecked OK", Toast.LENGTH_SHORT).show();
		}
		
	//create method for negative button click
		@NegativeButtonQD(REQUEST_UNIQUE_TAG)
		public void onNegativeButtonAnyNameMethode(){
			Toast.makeText(this, "Clicked cancel", Toast.LENGTH_SHORT).show();
		}
	}
	
```
	
	
	