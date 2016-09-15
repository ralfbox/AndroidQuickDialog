# Xamarin AndroidQuickDialog
Andorid library to the creation of dialogs. With this library can be created very quickly while keeping the code clean.

## Motivation
In the operating system Andorid creating the alert dialog that have support for screen rotation is very tiring. To create this we need to create a new static class based on DialogFragment and in it AlertDialog. When we get an answer to the Activity once again we have to write quite a lot of code. We need to call getActivity (), then cast it to the appropriate class, and then call the appropriate method. An even greater problem is when we want to answer to fragment. It is very sluggish!
So I decided to write a library that makes it easy to create Alert dialog with support for screen rotation and returning responses to the Activity or Fragment.



## Usage
#### Installation:
[Download library](https://raw.githubusercontent.com/ralfbox/AndroidQuickDialog/master/downloads/QuickDialog.aar)  
`File -> New -> New module -> import .JAR/.AAR Package -> pick QuickLibrary.aar -> Finish`


##### How to use:
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
	
	
## Contact
    pudelko[dot]rafal[at]gmail[dot]com
  

## License
Copyright (c) 2016, Rafal Pudelko

You should but you don't have to mention it in application UI with string **"Used QuickQialog (c) 2016, Rafal Pudelko"** (e.g. in "About" section).

Licensed under the [BSD 3-clause](http://www.opensource.org/licenses/BSD-3-Clause)